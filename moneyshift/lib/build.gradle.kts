import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.wpilib.gradlerio.wpi.WPIExtension
import org.wpilib.toolchain.NativePlatforms

plugins {
    `java-library`
    `maven-publish`
    id("org.wpilib.WPILibRepositoriesPlugin")
    id("org.wpilib.GradleRIO")
}

group = "org.teamvan.moneyshift"

repositories {
    mavenCentral()
}
wpilibRepositories.addAllReleaseRepositories(project)

val wpiExt = extensions.getByType(WPIExtension::class.java)

dependencies {
    api(libs.ntcore.java)
    api(libs.wpiutil.java)

    wpiExt.java.deps.wpilibJniRelease(NativePlatforms.desktop).forEach {
        add("nativeRelease", it.get())
    }

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing", "-Xlint:-serial"))
}

// The JDK bundles ~4 MB of DejaVu webfonts into the generated docs. Dropping them keeps the
// published javadoc jar well under the size budget; browsers fall back to system fonts.
tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addBooleanOption("-no-fonts", true)
}

// Published artifacts (jar, sources, javadoc) must each stay under 5 MB.
abstract class PublicationSizeCheck : DefaultTask() {
    @get:InputFiles
    abstract val artifacts: ConfigurableFileCollection

    @get:Input
    var maxBytes: Long = 5L * 1024 * 1024

    @TaskAction
    fun checkSizes() {
        var total = 0L
        artifacts.files.sortedBy { it.name }.forEach { file ->
            val size = file.length()
            total += size
            val mb = "%.2f".format(size.toDouble() / (1024 * 1024))
            logger.lifecycle("${file.name}: $size bytes ($mb MB)")
            if (size > maxBytes) {
                val budgetMb = "%.0f".format(maxBytes.toDouble() / (1024 * 1024))
                throw GradleException(
                    "${file.name} is $mb MB, which exceeds the $budgetMb MB publication budget",
                )
            }
        }
        logger.lifecycle("total: %.2f MB".format(total.toDouble() / (1024 * 1024)))
    }
}

val checkPublicationSize by tasks.registering(PublicationSizeCheck::class) {
    group = "verification"
    description = "Fails if a published artifact exceeds 5 MB."

    dependsOn("jar", "sourcesJar", "javadocJar")
    artifacts.from(
        tasks.named<Jar>("jar").flatMap { it.archiveFile },
        tasks.named<Jar>("sourcesJar").flatMap { it.archiveFile },
        tasks.named<Jar>("javadocJar").flatMap { it.archiveFile },
    )
}

tasks.named("check") {
    dependsOn(checkPublicationSize)
}

tasks.test {
    useJUnitPlatform()
    // Puts the extracted WPILib JNI libraries on java.library.path / LD_LIBRARY_PATH and makes
    // the test task depend on the extraction.
    wpiExt.java.configureTestTasks(this)
    // `-Dfirstrun.writeFixture=true` regenerates the cross-language wire fixture consumed by the
    // Rust test in `tests/wire_fixture.rs`.
    systemProperty("firstrun.writeFixture", System.getProperty("firstrun.writeFixture", "false"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
