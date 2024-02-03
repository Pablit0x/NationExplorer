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
                password =
                    "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"
            }
        }
    }
}


