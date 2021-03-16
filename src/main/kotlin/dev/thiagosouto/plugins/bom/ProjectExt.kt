package dev.thiagosouto.plugins.bom

import org.gradle.api.Project


fun Project.createDependencies(): List<Dependency> {
    val deps = mutableListOf<Dependency>()

    project.configurations
        .filter { it.name == "bomConfiguration"}
        .forEach { configuration ->
            configuration.allDependencies
                .forEach {
                    if(configuration.name == "bomConfiguration"){
                        deps.add(Dependency(it.name, it.group ?: "", it.version ?: "", emptyList()))
                    }
                }
        }
    return deps
}
