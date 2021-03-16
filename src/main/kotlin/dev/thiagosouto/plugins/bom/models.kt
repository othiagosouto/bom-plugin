package dev.thiagosouto.plugins.bom

data class BomInfo(val projectAttributes: List<Tag>, val tags: List<Tag>, val dependencies: List<Dependency>)
data class Tag(val name: String, val value: String)
data class Dependency(
    val artifactId: String,
    val groupId: String,
    val version: String,
    val exclusions: List<Exclusion> = emptyList()
)

data class Exclusion(val artifactId: String, val groupId: String)
