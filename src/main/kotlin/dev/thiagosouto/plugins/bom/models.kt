package dev.thiagosouto.plugins.bom

internal data class BomInfo(val projectAttributes: List<Tag>, val tags: List<Tag>, val dependencies: List<Dependency>)
internal class SimpleTag(name: String, val value: String) : Tag(name)
internal class RootTag(name: String, val tags: List<Tag>) : Tag(name)

internal data class Dependency(
    val artifactId: String,
    val groupId: String,
    val version: String,
    val exclusions: List<Exclusion> = emptyList()
)

internal data class Exclusion(val artifactId: String, val groupId: String)
internal abstract class Tag(val name: String)
