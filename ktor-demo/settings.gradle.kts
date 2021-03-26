pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "ktor-demo"
include("app_base", "app_a", "app_b")
