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
    }
}

rootProject.name = "T3"
include(":app")
include(":core:model")
include(":core:network")
include(":core:database")
include(":domain")
include(":data")
include(":feature:login")
include(":feature:home")
include(":feature:favorites")
include(":core:ui")
