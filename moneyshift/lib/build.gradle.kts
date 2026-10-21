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
