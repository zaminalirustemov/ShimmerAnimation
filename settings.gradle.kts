pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // Include JitPack, a repository that allows you to fetch dependencies from GitHub projects.
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Shimmer Effect"
include(":app")
include(":shimmer_animation")
