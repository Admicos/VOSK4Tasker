dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://alphacephei.com/maven/")
        }
    }
}
rootProject.name = "VOSK4Tasker"
include(":app")
