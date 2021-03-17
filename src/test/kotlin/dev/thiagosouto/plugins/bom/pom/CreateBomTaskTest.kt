package dev.thiagosouto.plugins.bom.pom

import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.butler.file.readFile
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CreateBomTaskTest {

    @get: Rule
    var testProjectDir: TemporaryFolder = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var gradleRunner: GradleRunner

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
        buildFile.appendText(
            """
                 plugins {
                    id "java"
                    id "bom-plugin"
                }
                bomMetadata {
                    artifactId = "artifact-bom"
                    description = "some description"
                    groupId = "dev.thiagosouto"
                    name = "Bill of Materials"
                    version = "0.1.0-SNAPSHOT"
                } 
                """
        )
    }

    @Test
    fun `createBomFile should generate BOM xml with exclusion rules`() {
        buildFile.appendText(
            """     
                dependencies {
                    implementation("junit:junit:4.13.2") {
                        exclude group: 'com.google.guava', module:  'guava'
                    }
                }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", "createBomFile").build()
        val generatedPomContent = File(testProjectDir.root.path + "/build/outputs/bom/pom.xml").readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom1_with_exclusion.xml"))
        assertThat(result.task(":createBomFile")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }


    @Test
    fun `createBomFile should generate BOM xml without exclusion rules`() {
        buildFile.appendText(
            """     
                dependencies {
                    implementation("junit:junit:4.13.2")
                }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", "createBomFile").build()
        val generatedPomContent = File(testProjectDir.root.path + "/build/outputs/bom/pom.xml").readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom1.xml"))
        assertThat(result.task(":createBomFile")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }
}