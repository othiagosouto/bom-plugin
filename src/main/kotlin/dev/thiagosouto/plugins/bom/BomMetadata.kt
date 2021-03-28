package dev.thiagosouto.plugins.bom

import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.maven.model.License
import org.gradle.kotlin.dsl.get

open class BomMetadata(
    var artifactId: String = "",
    var description: String = "",
    var groupId: String = "",
    var name: String = "",
    var version: String = "",
    var licenseName: String = "",
    var licenseUrl: String = "",
    var developerId : String = "",
    var developerName: String = "",
    var developerEmail: String = "",
    var scmConnection: String = "",
    var scmDeveloperConnection: String = "",
    var scmUrl: String = ""
) {
    companion object {
        const val EXT_NAME = "bomMetadata"
        fun fromProject(project: Project): BomMetadata {
            return project.extensions[EXT_NAME] as BomMetadata
        }
    }
}
