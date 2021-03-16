plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    signing
    kotlin("jvm") version "1.4.31"
    id("com.gradle.plugin-publish") version "0.13.0"
}

group  "dev.thiagosouto"
version "0.2-SNAPSHOT"

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
            id = "bom-plugin"
            implementationClass = "dev.thiagosouto.plugins.bom.BomPlugin"
        }
    }

}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.7.2")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.2")
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

publishing {
    val ossrhUsername: String by project
    val ossrhPassword: String by project

    repositories {
        maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
    publications {
        group = "dev.thiagosouto"
        version = "0.2-SNAPSHOT"
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("bom-plugin")
                description.set("A library to help apply tdd through help functions");
                url.set("https://thiagosouto.dev")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/othiagosouto/bom-plugin.git/")
                    developerConnection.set("scm:git:ssh://github.com:othiagosouto/bom-plugin.git")
                    url.set("https://github.com/othiagosouto/bom-plugin")
                }
                developers {
                    developer {
                        id.set("othiagosouto")
                        name.set("Thiago Souto silva de barros Santos")
                        email.set("soutosss@gmail.com")
                    }
                }
            }
        }
    }
}
afterEvaluate {
    signing {
        sign(publishing.publications["mavenJava"])
    }
}