plugins {
    application
    id("org.wpilib.WPILibRepositoriesPlugin")
}

repositories {
    mavenCentral()
}
wpilibRepositories.addAllReleaseRepositories(project)

val nativesDir = rootProject.layout.buildDirectory.dir("natives")

dependencies {
    implementation(project(":lib"))

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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing", "-Xlint:-serial"))
}

tasks.withType<JavaExec>().configureEach {
    dependsOn(rootProject.tasks.named("extractWpilibNatives"))
    systemProperty("java.library.path", nativesDir.get().asFile.absolutePath)
    environment("LD_LIBRARY_PATH", nativesDir.get().asFile.absolutePath)
}

tasks.test {
    useJUnitPlatform()
    dependsOn(rootProject.tasks.named("extractWpilibNatives"))
    systemProperty("java.library.path", nativesDir.get().asFile.absolutePath)
    environment("LD_LIBRARY_PATH", nativesDir.get().asFile.absolutePath)
}
