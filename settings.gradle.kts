import java.net.URI

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
        maven { url = URI.create("https://vbmobileidstorage.blob.core.windows.net/android/") }
        maven {
            url = URI.create("http://maven.regulaforensics.com/RegulaDocumentReader")
            isAllowInsecureProtocol=  true
        }
    }
}

rootProject.name = "SampleComposeApp"
include(":app")
 