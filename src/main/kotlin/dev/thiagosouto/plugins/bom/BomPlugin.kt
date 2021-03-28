package dev.thiagosouto.plugins.bom

import dev.thiagosouto.plugins.bom.dependencies.creator.CreateBomClassTask
import dev.thiagosouto.plugins.bom.pom.CreateBomTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer

class BomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.createBomMetadataExtension()
        val bomConfiguration = target.configurations.createConfiguration("bomConfiguration")
        target.configurations.getByName("implementation") {
            this.extendsFrom(bomConfiguration)
        }

        target.tasks.create("createBomFile", CreateBomTask::class.java)
        target.tasks.create("createBomClass", CreateBomClassTask::class.java)
    }

    private fun ConfigurationContainer.createConfiguration(name: String): Configuration {
        return this.create(name)
    }
}
