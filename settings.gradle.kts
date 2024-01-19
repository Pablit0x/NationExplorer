rootProject.name = "FunApp"
include(":composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/carlosgub/kotlinm-charts")

            credentials {
                username = "Pablit0x"
                password = "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"
            }
        }
    }
}


