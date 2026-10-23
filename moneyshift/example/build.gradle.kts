import org.wpilib.gradlerio.wpi.WPIExtension
import org.wpilib.toolchain.NativePlatforms

plugins {
    application
    id("org.wpilib.WPILibRepositoriesPlugin")
    id("org.wpilib.GradleRIO")
}

repositories {
    mavenCentral()
}
wpilibRepositories.addAllReleaseRepositories(project)

val wpiExt = extensions.getByType(WPIExtension::class.java)

dependencies {
    implementation(project(":lib"))

    // GradleRIO resolves the WPILib JNI libraries for this machine and unpacks them into
    // build/jni/release (see `extractReleaseNative`).
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
}

application {
    mainClass = "org.teamvan.moneyshift.example.MoneyshiftDemo"
}

// GradleRIO's simulation-aware `configureApplication` is not configuration-cache compatible, so
// point `run` at the extracted libraries directly.
val nativeLibs = wpiExt.java.extractNativeReleaseArtifacts.flatMap { it.destinationDirectory }

tasks.named<JavaExec>("run") {
    dependsOn(wpiExt.java.extractNativeReleaseArtifacts)
    val dir = nativeLibs.get().asFile.absolutePath
    systemProperty("java.library.path", dir)
    environment("LD_LIBRARY_PATH", dir)
    environment("DYLD_FALLBACK_LIBRARY_PATH", dir)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing", "-Xlint:-serial"))
}

tasks.test {
    useJUnitPlatform()
    wpiExt.java.configureTestTasks(this)
}
