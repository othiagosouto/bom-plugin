package dev.thiagosouto.plugins.bom.dependencies.creator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import dev.thiagosouto.plugins.bom.Dependency
import dev.thiagosouto.plugins.bom.convertToClassName
import dev.thiagosouto.plugins.bom.convertToFieldName
import java.io.File
import kotlin.reflect.KClass

internal class DependencyCreator(path: String) {
    private val filePath = File(path)

    fun generate(packageName: String, className: String, dependenciesList: List<Dependency>) {
        val content: Map<String, List<Dependency>> = dependenciesList.groupBy { it.groupId }
        val groupTypeProperties: MutableList<PropertySpec> = mutableListOf()
        val bomClass = TypeSpec.objectBuilder(className)

        for ((groupId, dependenciesByGroup) in content) {
            val groupClass = createGroupClassAndFile(packageName, groupId, dependenciesByGroup)

            PropertySpec.builder(groupId.convertToFieldName(), TypeVariableName(groupId.convertToClassName())).apply {
                initializer("${groupId.convertToClassName()}()", groupClass)
                groupTypeProperties.add(this.build())
            }
        }

        val bomFile = FileSpec.builder(packageName, className)
        groupTypeProperties.forEach {
            bomClass.addProperty(it)
        }
        bomFile.addType(bomClass.build()).build().writeTo(filePath)
    }

    private fun createGroupClassAndFile(packageName: String, groupId: String, dependenciesByGroup: List<Dependency>):
            KClass<out FileSpec> {
        val dependencyGroupClass = TypeSpec.classBuilder(groupId.convertToClassName())
        dependencyGroupClass.addProperties(dependenciesByGroup.toPropertiesList())

        val dependencyGroupFile = FileSpec.builder(packageName, groupId.convertToClassName())
        dependencyGroupFile.addType(dependencyGroupClass.build())

        val group = dependencyGroupFile.build()
        group.writeTo(filePath)
        return group::class
    }

    private fun List<Dependency>.toPropertiesList(): List<PropertySpec> {
        val properties = mutableListOf<PropertySpec>()
        for (dependencyName in this) {
            val property = PropertySpec.builder(dependencyName.artifactId.convertToFieldName(), String::class)
                .initializer("\"${dependencyName.groupId}:${dependencyName.artifactId}\"")
                .build()
            properties.add(property)
        }
        return properties
    }
}
