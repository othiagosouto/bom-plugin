package dev.thiagosouto.plugins.bom.dependencies.creator

import dev.thiagosouto.plugins.bom.BomMetadata
import dev.thiagosouto.plugins.bom.createDependencies
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CreateBomClassTask : DefaultTask() {

    @TaskAction
    fun create() {
        val bomMetadata = BomMetadata.fromProject(project)
        val item = DependencyCreator("${project.buildDir}/outputs/bom")
        item.generate(bomMetadata.groupId, "Bom", project.createDependencies())
    }
}
