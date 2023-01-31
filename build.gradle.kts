plugins {
    java
    `kotlin-dsl`
    kotlin("jvm") version "1.8.0"
    id("java-gradle-plugin")
    id("maven-publish")
    jacoco
    id("com.gradle.plugin-publish") version "0.12.0"
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
}

group = "dev.thiagosouto"
version = File("VERSION.txt").readText()

description = "A plugin to generate BOM from gradle projects"

jacoco {
    toolVersion = "0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
    }
}

gradlePlugin {
    plugins {
        create("bom-plugin") {
            id = "dev.thiagosouto.plugins.bom-plugin"
            implementationClass = "dev.thiagosouto.plugins.bom.BomPlugin"
            displayName = "bom-plugin"
            description = "A plugin to generate BOM from gradle projects"
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.12.0")

    testImplementation("com.google.truth:truth:1.1.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("dev.thiagosouto:file-butler:0.3.0")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.16.0")
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
    website = "https://github.thiagosouto.dev"
    vcsUrl = "https://github.com/othiagosouto/bom-plugin"
    tags = listOf("gradle", "BOM", "plugin", "android", "pom")
}
