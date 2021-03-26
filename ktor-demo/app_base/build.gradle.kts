import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logback_version: String by rootProject
val ktor_version: String by rootProject
val kotlin_version: String by rootProject
val arrow_version: String by rootProject
val dapr_version: String by rootProject

plugins {
    kotlin("jvm")
}

group = "com.joram"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    api("io.ktor:ktor-server-netty:$ktor_version")
    api("ch.qos.logback:logback-classic:$logback_version")
    api("io.ktor:ktor-server-core:$ktor_version")
    api("io.ktor:ktor-gson:$ktor_version")
    api("io.arrow-kt:arrow-fx-coroutines:$arrow_version")
    api("io.dapr:dapr-sdk:$dapr_version")
    api("io.ktor:ktor-server-tests:$ktor_version")
}
