package dev.thiagosouto.plugins.bom

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.kotlin.dsl.create

internal fun Project.createDependencies(): List<Dependency> {
    val dependencies = mutableListOf<Dependency>()
    project
        .configurations
        .filter { it.name == "bomConfiguration" }
        .forEach { config ->
            config.dependencies.forEach {
                dependencies.add(Dependency(it.name, it.group ?: "", it.version ?: "", getExclusions(it)))
            }
        }

    return dependencies
}

private fun getExclusions(dependency: org.gradle.api.artifacts.Dependency): List<Exclusion> {
    val exclusions = mutableListOf<Exclusion>()
    if (dependency is ModuleDependency) {
        dependency.excludeRules.forEach {
            exclusions.add(Exclusion(it.module, it.group))
        }
    }
    return exclusions
}

internal fun Project.createBomMetadataExtension() {
    this.extensions.create<BomMetadata>(BomMetadata.EXT_NAME)
}
