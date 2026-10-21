import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.external.javadoc.StandardJavadocDocletOptions

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.arrow.vector)
    api(libs.arrow.memory.core)
    api(libs.grpc.api)
    api(libs.grpc.core)
    api(libs.grpc.stub)
    api(libs.grpc.protobuf)
    api(libs.protobuf.java)

    implementation(libs.lz4.java)
    runtimeOnly(libs.grpc.netty.shaded)
    runtimeOnly(libs.arrow.memory.unsafe)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.grpc.netty.shaded)
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
    // Arrow uses direct memory and requires reflective access on modern JDKs.
    jvmArgs(
        "--add-opens=java.base/java.nio=ALL-UNNAMED",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "-Dio.netty.tryReflectionSetAccessible=true",
    )
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
