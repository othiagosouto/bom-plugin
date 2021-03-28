package dev.thiagosouto.plugins.bom

internal data class BomInfo(val projectAttributes: List<Tag>, val tags: List<Tag>, val dependencies: List<Dependency>)
internal class SimpleTag(override val name: String, val value: String) : Tag

internal class RootTag(override val name: String, val tags: List<Tag>) : Tag

internal data class Dependency(
    val artifactId: String,
    val groupId: String,
    val version: String,
    val exclusions: List<Exclusion> = emptyList()
)

internal data class Exclusion(val artifactId: String, val groupId: String)
interface Tag {
    val name: String
}
