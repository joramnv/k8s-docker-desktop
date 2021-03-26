import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.joram"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

apply {
    plugin("application")
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(project(":app_base"))
}

/* Tasks */
tasks.withType<ShadowJar> {
    manifest {
        attributes.apply {
            put("Main-Class", application.mainClassName)
        }
    }
}
