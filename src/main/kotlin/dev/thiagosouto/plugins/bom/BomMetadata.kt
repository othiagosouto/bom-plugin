package dev.thiagosouto.plugins.bom

import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

open class BomMetadata(
    var artifactId: String = "",
    var description: String = "",
    var groupId: String = "",
    var name: String = "",
    var version: String = ""
) {
    companion object {
        const val EXT_NAME = "bomMetadata"
        fun fromProject(project: Project): BomMetadata {
            return project.extensions[EXT_NAME] as BomMetadata
        }
    }
}