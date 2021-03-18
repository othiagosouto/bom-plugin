package dev.thiagosouto.plugins.bom.dependencies_creator

import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import dev.thiagosouto.butler.file.readFile
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CreateBomClassTaskTest {

    @get: Rule
    var testProjectDir: TemporaryFolder = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var gradleRunner: GradleRunner

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
     }

    @Test
    fun `createBomFile should generate kotlin classes as expected`() {
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
                    implementation "junit:junit:4.13.2"
                }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("createBomClass").build()
        val bomClass = File(testProjectDir.root.path + "/build/outputs/bom/dev/thiagosouto/Bom.kt").readText()

        val jUnitClass = File(testProjectDir.root.path + "/build/outputs/bom/dev/thiagosouto/Junit.kt").readText()
        assertThat(bomClass).isEqualTo(readFile("plugin_results/Bom.kt"))
        assertThat(jUnitClass).isEqualTo(readFile("plugin_results/Junit.kt"))
        assertThat(result.task(":createBomClass")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

}