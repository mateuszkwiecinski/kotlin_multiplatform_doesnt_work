import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version "1.6.10"
}

kotlin {
    jvm()

    val iOSFrameworkName = "Foo"
    val xcf = XCFramework(iOSFrameworkName)

    listOf(
        iosX64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = iOSFrameworkName
            freeCompilerArgs = freeCompilerArgs + listOf("-linker-options", "-application_extension")
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting

        val iosX64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
        }
    }
}

dependencies {
    commonMainImplementation(kotlin("stdlib-common"))
    commonTestImplementation(kotlin("test-common"))
    commonTestImplementation(kotlin("test-annotations-common"))
}
