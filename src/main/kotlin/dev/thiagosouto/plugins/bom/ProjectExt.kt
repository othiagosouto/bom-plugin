package dev.thiagosouto.plugins.bom

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency

fun Project.createDependencies(): List<Dependency> {
    val deps = mutableListOf<Dependency>()
    project
        .configurations
        .filter { it.name == "bomConfiguration" }
        .forEach { config ->
            config.dependencies.forEach {
                deps.add(Dependency(it.name, it.group ?: "", it.version ?: "", getExclusions(it)))
            }
        }

    return deps
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
