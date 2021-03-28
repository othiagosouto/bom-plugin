package dev.thiagosouto.plugins.bom.pom

import dev.thiagosouto.plugins.bom.BomInfo
import dev.thiagosouto.plugins.bom.Dependency
import dev.thiagosouto.plugins.bom.Exclusion
import dev.thiagosouto.plugins.bom.SimpleTag
import dev.thiagosouto.plugins.bom.Tag
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class BOMDocumentCreator {

    fun create(bomInfo: BomInfo): Node {
        val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc: Document = docBuilder.newDocument()

        val rootElement: Element = doc.createElement(PROJECT)

        createMavenAttributes(rootElement, bomInfo.projectAttributes)

        appendPomInfo(doc, rootElement, bomInfo.tags)

        rootElement.appendChild(createDependencies(doc, bomInfo.dependencies))

        doc.appendChild(rootElement)

        return doc
    }

    private fun createMavenAttributes(rootElement: Element, projectAttributes: List<Tag>) {
        for (attribute in projectAttributes) {
            if (attribute is SimpleTag) {
                rootElement.setAttribute(attribute.name, attribute.value)
            }
        }
    }

    private fun appendPomInfo(document: Document, rootElement: Element, tags: List<Tag>) {
        for (tag in tags) {
            if (tag is SimpleTag) {
                rootElement.appendChild(createTagValueElement(document, tag.name, tag.value))
            }
        }
    }

    private fun createDependencies(document: Document, dependencies: List<Dependency>): Element {
        val dependencyManagementElement = document.createElement(DEPENDENCY_MANAGEMENT)
        val dependenciesElement = document.createElement(DEPENDENCIES)

        for (dependencyInfo in dependencies) {
            val dependencyNode = document createDependencyNodeFrom dependencyInfo
            if (dependencyInfo.exclusions.isNotEmpty()) {
                val exclusionsElement = document createExclusionsNodeFrom dependencyInfo
                dependencyNode.appendChild(exclusionsElement)
            }
            dependenciesElement.appendChild(dependencyNode)
        }
        dependencyManagementElement.appendChild(dependenciesElement)
        return dependencyManagementElement
    }

    private infix fun Document.createExclusionsNodeFrom(dependency: Dependency): Element {
        val exclusionsElement = this.createElement(EXCLUSIONS)
        for (exclusionInfo in dependency.exclusions) {
            val exclusionElement = this createExclusionNodeFrom exclusionInfo
            exclusionsElement.appendChild(exclusionElement)
        }
        return exclusionsElement
    }

    private infix fun Document.createDependencyNodeFrom(dependencyInfo: Dependency): Element {
        val dep = createElement(DEPENDENCY)
        dep.appendChild(createTagValueElement(this, ARTIFACT_ID, dependencyInfo.artifactId))
        dep.appendChild(createTagValueElement(this, GROUP_ID, dependencyInfo.groupId))
        dep.appendChild(createTagValueElement(this, VERSION, dependencyInfo.version))
        return dep
    }

    private infix fun Document.createExclusionNodeFrom(exclusionInfo: Exclusion): Element {
        val exclusionElement = this.createElement(EXCLUSION)
        exclusionElement.appendChild(createTagValueElement(this, GROUP_ID, exclusionInfo.groupId))
        exclusionElement.appendChild(createTagValueElement(this, ARTIFACT_ID, exclusionInfo.artifactId))
        return exclusionElement
    }

    companion object {
        private const val ARTIFACT_ID = "artifactId"
        private const val DEPENDENCY = "dependency"
        private const val DEPENDENCIES = "dependencies"
        private const val GROUP_ID = "groupId"
        private const val VERSION = "version"
        private const val EXCLUSION = "exclusion"
        private const val PROJECT = "project"
        private const val DEPENDENCY_MANAGEMENT = "dependencyManagement"
        private const val EXCLUSIONS = "exclusions"
    }
}
