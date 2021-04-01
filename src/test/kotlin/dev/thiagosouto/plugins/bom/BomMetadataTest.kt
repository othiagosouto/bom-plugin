package dev.thiagosouto.plugins.bom

import com.google.common.truth.Truth.assertThat
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

internal class BomMetadataTest {
    private lateinit var project: Project

    @Before
    fun setup() {
        project = ProjectBuilder.builder().withName("project").build()
    }

    @Test
    fun `should return bomMetadatada`() {
        project.createBomMetadataExtension()

        assertThat(BomMetadata.fromProject(project)).isInstanceOf(BomMetadata::class.java)
    }
}
