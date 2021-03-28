package dev.thiagosouto.plugins.bom.pom

import dev.thiagosouto.plugins.bom.BomInfo
import dev.thiagosouto.plugins.bom.BomMetadata
import dev.thiagosouto.plugins.bom.SimpleTag
import dev.thiagosouto.plugins.bom.Tag
import dev.thiagosouto.plugins.bom.createDependencies
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Node
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

open class CreateBomTask : DefaultTask() {

    @TaskAction
    fun create() {
        val creator = BOMDocumentCreator()
        val bomMetadata = BomMetadata.fromProject(project)
        val bomInfo = BomInfo(createProjectAttributes(), createProjectTags(bomMetadata), project.createDependencies())
        Files.createDirectories(Paths.get("${project.buildDir}/outputs/bom/"))
        createXml(creator.create(bomInfo), "${project.buildDir}/outputs/bom/pom.xml")
    }

    private fun createXml(element: Node, fileName: String) {
        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
        transformer.transform(DOMSource(element), StreamResult(File(fileName)))
    }

    private fun createProjectAttributes(): List<Tag> {
        val attrs = mutableListOf<Tag>()
        attrs.add(SimpleTag("xmlns", "http://maven.apache.org/POM/4.0.0"))
        attrs.add(SimpleTag("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"))
        attrs.add(
            SimpleTag(
                "xsi:schemaLocation",
                "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            )
        )
        return attrs
    }

    private fun createProjectTags(projectInfo: BomMetadata): List<Tag> {
        val attrs = mutableListOf<Tag>()
        attrs.add(SimpleTag("modelVersion", "4.0.0"))
        attrs.add(SimpleTag("groupId", projectInfo.groupId))
        attrs.add(SimpleTag("artifactId", projectInfo.artifactId))
        attrs.add(SimpleTag("version", projectInfo.version))
        attrs.add(SimpleTag("packaging", "pom"))
        attrs.add(SimpleTag("description", projectInfo.description))
        attrs.add(SimpleTag("name", projectInfo.name))
        return attrs
    }
}
