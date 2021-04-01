package dev.thiagosouto.plugins.bom.pom

import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.butler.file.readFile
import dev.thiagosouto.plugins.bom.BomInfo
import dev.thiagosouto.plugins.bom.Dependency
import dev.thiagosouto.plugins.bom.Exclusion
import dev.thiagosouto.plugins.bom.SimpleTag
import dev.thiagosouto.plugins.bom.Tag
import dev.thiagosouto.plugins.bom.TestUtils
import org.junit.Before
import org.junit.Test

internal class BOMDocumentCreatorTest {
    private lateinit var creator: BOMDocumentCreator
    private val projectTags: List<Tag>
        get() {
            val attrs = mutableListOf<Tag>()
            attrs.add(SimpleTag("modelVersion", "4.0.0"))
            attrs.add(SimpleTag("groupId", "dev.thiagosouto"))
            attrs.add(SimpleTag("artifactId", "bom-validation"))
            attrs.add(SimpleTag("version", "1.0"))
            attrs.add(SimpleTag("packaging", "pom"))
            return attrs
        }
    private val projectAttributes: List<Tag>
        get() {
            val attrs = mutableListOf<Tag>()
            attrs.add(SimpleTag("xmlns", "http://maven.apache.org/POM/4.0.0"))
            attrs.add(SimpleTag("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"))
            attrs.add(
                SimpleTag(
                    "xsi:schemaLocation",
                    "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
                )
            )
            return attrs
        }

    @Before
    fun setup() {
        creator = BOMDocumentCreator()
    }

    @Test
    fun `should create pom without exclusion`() {
        val bomInfo = BomInfo(projectAttributes, projectTags, createDependencies())

        val element = creator.create(bomInfo)
        val content = TestUtils.getNodeString(element, "testPom")

        assertThat(content).isEqualTo(readFile("pom_without_exclusion.xml"))
    }

    @Test
    fun `should create pom without exclusionInfo`() {
        val bomInfo = BomInfo(projectAttributes, projectTags, createDependencies(true))
        val element = creator.create(bomInfo)

        val content = TestUtils.getNodeString(element, "testPom")

        assertThat(content).isEqualTo(readFile("pom_with_exclusions.xml"))
    }

    private fun createDependencies(isExclusion: Boolean = false): List<Dependency> {
        val dependencies = mutableListOf<Dependency>()
        for (i in 0..10) {
            val exclusions: List<Exclusion> = if (isExclusion) createExclusions() else emptyList()
            dependencies.add(Dependency("artifactId$i", "groupId$i", "version$i", exclusions))
        }

        return dependencies
    }

    private fun createExclusions(): List<Exclusion> {
        val exclusions = mutableListOf<Exclusion>()

        for (i in 0..3) {
            exclusions.add(Exclusion("artifactIdExclusion$i", "groupIdExclusion$i"))
        }
        return exclusions
    }
}
