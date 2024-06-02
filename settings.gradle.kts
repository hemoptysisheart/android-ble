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

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            val properties = java.util.Properties()
            properties.load(rootProject.projectDir.resolve("local.properties").inputStream())

            url = uri("https://maven.pkg.github.com/hemoptysisheart/packages")
            credentials {
                username = properties["publish.user"] as String?
                    ?: System.getenv("GITHUB_ACTOR")
                password = properties["publish.token"] as String?
                    ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

rootProject.name = "android-template"

include(
    ":app",
    ":ui",
    ":viewmodel",
    ":model"
)
