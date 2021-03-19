plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
    kotlin("jvm") version "1.4.31"
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.12.0"
}

group ="dev.thiagosouto"
version ="0.3"
description = "A plugin to generate BOM from gradle projects"


buildscript {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

gradlePlugin {
    plugins {
        create("bom-plugin") {
            id = "dev.thiagosouto.plugins.bom-plugin"
            implementationClass = "dev.thiagosouto.plugins.bom.BomPlugin"
            displayName= "bom-plugin"
            description = "A plugin to generate BOM from gradle projects"
        }
    }

}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.7.2")

    testImplementation("com.google.truth:truth:1.1.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("dev.thiagosouto:file-butler:0.3.0")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

sourceSets.test {
    java.srcDirs("src/test/kotlin")
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}

pluginBundle {
    website = "https://thiagosouto.dev"
    vcsUrl = "https://github.com/othiagosouto/bom-plugin"
    tags = listOf("gradle", "BOM", "plugin", "android", "pom")
}

