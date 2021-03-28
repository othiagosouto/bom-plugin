package dev.thiagosouto.plugins.bom

import com.google.common.truth.Truth.assertThat
import org.gradle.api.Project
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.get
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class ProjectExtKtTest {
    private lateinit var project: Project

    @Before
    fun setup() {
        project = ProjectBuilder.builder().withName("project").build()
        project.configurations.create("bomConfiguration")
        project.configurations.create("implementation")
    }

    @Test
    fun `should parse dependencies with  as expected`() {
        val expectedDependencies = mutableListOf<Dependency>()
        expectedDependencies.add(Dependency("plugin", "dev.thiagosouto", "1.3.4"))
        expectedDependencies.add(Dependency("sample", "dev.thiagosouto", "1.3.5"))

        project.dependencies.add("bomConfiguration", "dev.thiagosouto:plugin:1.3.4")
        project.dependencies.add("bomConfiguration", "dev.thiagosouto:sample:1.3.5")
        project.dependencies.add("implementation", "dev.thiagosouto:exclude:1.3.5")

        assertThat(project.createDependencies()).isEqualTo(expectedDependencies)
    }

    @Test
    fun `should not filter dependencies that isn't bomConfiguration `() {
        project.dependencies.add("implementation", "dev.thiagosouto:some1:1.3.5")
        project.dependencies.add("implementation", "dev.thiagosouto:some:1.3.9")

        assertThat(project.createDependencies()).isEmpty()
    }

    @Test
    fun `should build dependency with exclusion rules`() {
        val expectedDependencies = mutableListOf<Dependency>()
        expectedDependencies.add(Dependency("plugin", "dev.thiagosouto", "1.3.4", listOf(Exclusion("test1", "dev.thiagosouto"))))

        project.dependencies.add("bomConfiguration", "dev.thiagosouto:plugin:1.3.4") {
            this.exclude(mapOf("group" to "dev.thiagosouto", "module" to "test1"))
        }

        assertThat(project.createDependencies()).isEqualTo(expectedDependencies)
    }

    @Test
    fun `should create bomMetadataExtension`() {
        project.createBomMetadataExtension()

        assertThat(project.extensions["bomMetadata"]).isInstanceOf(BomMetadata::class.java)
    }
}
