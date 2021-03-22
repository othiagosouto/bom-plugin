package dev.thiagosouto.plugins.bom

import dev.thiagosouto.plugins.bom.dependencies.creator.CreateBomClassTask
import dev.thiagosouto.plugins.bom.pom.CreateBomTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.kotlin.dsl.create

class BomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.createBomMetadataExtension()
        val configuration = target.configurations.createConfiguration("bomConfiguration")
        target.configurations.getByName("implementation") {
            configuration.extendsFrom(this)
        }

        target.tasks.create("createBomFile", CreateBomTask::class.java)
        target.tasks.create("createBomClass", CreateBomClassTask::class.java)
    }

    private fun ConfigurationContainer.createConfiguration(name: String): Configuration {
        return this.create(name)
    }
}

fun Project.createBomMetadataExtension() {
    this.extensions.create<BomMetadata>(BomMetadata.EXT_NAME)
}
