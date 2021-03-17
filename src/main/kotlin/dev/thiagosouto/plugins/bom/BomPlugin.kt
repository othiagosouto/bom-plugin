package dev.thiagosouto.plugins.bom

import dev.thiagosouto.plugins.bom.dependencies_creator.CreateBomClassTask
import dev.thiagosouto.plugins.bom.pom.CreateBomTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.kotlin.dsl.create

class BomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.createBomMetadataExtension()

        target.tasks.create("createBomFile", CreateBomTask::class.java)
        target.tasks.create("createBomClass", CreateBomClassTask::class.java)
    }

    private fun ConfigurationContainer.createConfiguration(name: String) {
        val configuration = this.create(name)
        add(configuration)
    }

}

fun Project.createBomMetadataExtension() {
    this.extensions.create<BomMetadata>(BomMetadata.EXT_NAME)
}
