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

internal class CreateBomTaskTest {

    @get: Rule
    var testProjectDir: TemporaryFolder = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var gradleRunner: GradleRunner
    private val bomPath = "/build/outputs/bom/pom.xml"
    private val createBomFileTaskName = "createBomFile"
    private val createBomFileTaskResult = ":createBomFile"

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
        buildFile.appendText(
            """
                     plugins {
                        id "java"
                        id "dev.thiagosouto.plugins.bom-plugin"
                     }
                 
                """
        )
    }

    @Test
    fun `createBomFile should generate BOM xml with exclusion rules`() {
        buildFile.appendText(
            """  
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                        projectUrl = "https://www.google.com"
                    }
                    
                    dependencies {
                        bomConfiguration("junit:junit:4.13.2") {
                            exclude group: 'com.google.guava', module:  'guava'
                        }
                    }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path + bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom1_with_exclusion.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }


    @Test
    fun `createBomFile should generate BOM xml without exclusion rules`() {
        buildFile.appendText(
            """
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                    }
                    
                    dependencies {
                        bomConfiguration("junit:junit:4.13.2")
                    }
            """
        )
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path + bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom1.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

    @Test
    fun `should build pom with expected license`() {
        buildFile.appendText(
            """
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                        licenseName = "Apache License, Version 2.0"
                        licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                """
        )

        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path +bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom_with_license.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

    @Test
    fun `should build pom with developers info`() {
        buildFile.appendText(
            """
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                        developerId = "othiagosouto"
                        developerName = "Thiago Souto Silva de Barros Santos"
                        developerEmail = "soutosss@gmail.com"
                    }
                """
        )

        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path + bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom_with_developers.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

    @Test
    fun `should build pom with scm info`() {
        buildFile.appendText(
            """
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                        scmConnection = "scm:git:git://github.com/othiagosouto/test-butler.git/"
                        scmDeveloperConnection = "'scm:git:ssh://github.com:othiagosouto/test-butler.git"
                        scmUrl = "https://github.com/othiagosouto/bom-plugin"
                    }
                """
        )

        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path + bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom_with_scm.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }


    @Test
    fun `should build pom GPG plugin execution`() {
        buildFile.appendText(
            """
                    bomMetadata {
                        artifactId = "artifact-bom"
                        description = "some description"
                        groupId = "dev.thiagosouto"
                        name = "Bill of Materials"
                        version = "0.1.0-SNAPSHOT"
                        gpgSign  = true
                    }
                """
        )

        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withDebug(true)
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())

        val result = gradleRunner.withArguments("build", createBomFileTaskName).build()
        val generatedPomContent = File(testProjectDir.root.path + bomPath).readText()
        assertThat(generatedPomContent).isEqualTo(readFile("plugin_results/pom1_with_gpg.xml"))
        assertThat(result.task(createBomFileTaskResult)!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }
}
