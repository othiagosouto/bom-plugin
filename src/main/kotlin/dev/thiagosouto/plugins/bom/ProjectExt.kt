package dev.thiagosouto.plugins.bom

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency

fun Project.createDependencies(): List<Dependency> {
    val deps = mutableListOf<Dependency>()
    project
        .configurations
        .filter { it.name == "bomConfiguration" }
        .forEach { config ->
            config.dependencies.forEach { dependency ->
                if (dependency is ModuleDependency) {
                    val exclusions = mutableListOf<Exclusion>()
                    dependency.excludeRules.forEach {
                        exclusions.add(Exclusion(it.module, it.group))
                    }
                    deps.add(Dependency(dependency.name, dependency.group ?: "", dependency.version ?: "", exclusions))
                }
            }
        }
    return deps
}
