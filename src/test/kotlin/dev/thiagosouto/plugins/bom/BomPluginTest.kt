package dev.thiagosouto.plugins.bom

import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class BomPluginTest{
    @get: Rule
    var testProjectDir: TemporaryFolder = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var gradleRunner: GradleRunner

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")

    }

    @Test
    fun `should build bomMetadata as expected task`() {
        buildFile.writeText(
            """
                 plugins {
                    id "java"
                    id "dev.thiagosouto.plugins.bom-plugin"
                }
                bomMetadata {
                    artifactId = "artifact-bom"
                    description = "some description"
                    groupId = "dev.thiagosouto"
                    name = "Bill of Materials"
                    version = "0.1.0-SNAPSHOT"
                }          
                dependencies {
                    bomConfiguration "junit:junit:4.13.2"
                    bomConfiguration  "com.google.truth:truth:1.1.2"
                }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

       gradleRunner.build()

    }

}