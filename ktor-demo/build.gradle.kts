import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val arrow_version: String by project
val dapr_version: String by project

plugins {
    base
    kotlin("jvm") version "1.4.32"
}

allprojects {
    group = "com.joram"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    group = "com.joram"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }

    val implementation by configurations

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
    }

    kotlin.sourceSets["main"].kotlin.srcDirs("src")
    kotlin.sourceSets["test"].kotlin.srcDirs("test")

    sourceSets["main"].resources.srcDirs("resources")
    sourceSets["test"].resources.srcDirs("testresources")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
