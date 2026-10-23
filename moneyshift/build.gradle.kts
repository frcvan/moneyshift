// Resolve WPILib from the WPILib Gradle plugins' repositories, the way AdvantageKit does.
plugins {
    id("org.wpilib.WPILibRepositoriesPlugin")
}

// WPILib publishes its JNI shared libraries inside the `-cpp` platform zips (there are no
// `-jni` artifacts since 2026). Resolve the zips for the build machine and unpack the libraries
// into `build/natives`, which the `lib` and `example` tests put on `java.library.path`.
val wpilibVersion = libs.versions.wpilib.get()

val osName = System.getProperty("os.name").lowercase()
val osArch = System.getProperty("os.arch").lowercase()
val wpilibClassifier: String =
    when {
        osName.startsWith("linux") && osArch in setOf("amd64", "x86_64") -> "linuxx86-64"
        osName.startsWith("linux") -> "linuxarm64"
        osName.startsWith("mac") -> "osxuniversal"
        osName.startsWith("windows") -> "windowsx86-64"
        else -> error("Unsupported operating system for WPILib natives: $osName")
    }

repositories {
    mavenCentral()
}
wpilibRepositories.addAllReleaseRepositories(project)

val wpilibNatives: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    wpilibNatives("org.wpilib.ntcore:ntcore-cpp:$wpilibVersion:$wpilibClassifier@zip")
    wpilibNatives("org.wpilib.wpiutil:wpiutil-cpp:$wpilibVersion:$wpilibClassifier@zip")
    wpilibNatives("org.wpilib.wpinet:wpinet-cpp:$wpilibVersion:$wpilibClassifier@zip")
    // `ntcore` links against datalog; its JNI library will not load without it.
    wpilibNatives("org.wpilib.datalog:datalog-cpp:$wpilibVersion:$wpilibClassifier@zip")
}

val extractWpilibNatives by tasks.registering(Sync::class) {
    group = "build"
    description = "Unpacks the WPILib 2027 JNI libraries into build/natives."
    from(
        wpilibNatives.incoming.artifactView {}.files.elements.map { elements ->
            elements.map { zipTree(it.asFile) }
        },
    )
    include("**/*.so")
    include("**/*.dylib")
    include("**/*.dll")
    includeEmptyDirs = false
    eachFile { relativePath = RelativePath(true, name) }
    into(layout.buildDirectory.dir("natives"))
}
