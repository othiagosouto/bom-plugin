# Bom-Plugin
A gradle plugin that helps you create a [maven bom](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) and all constants related to its dependencies.

### Features
- Generate Bill of materials `pom.xml`
- Generate Kotlin classes to represent all dependencies from the bill of materials

### Setup
#### Using kotlin
plugin-DSL:

```kotlin
plugins {
    id("dev.thiagosouto.plugins.bom-plugin") version "0.3"
}
bomMetadata{
    artifactId = "some-artifact-id" // The id of the artifact(pom.xml) created
    description = "some description" // artifact description
    groupId = "some.group.id" //  The id for the project group.
    name = "some-name" // Name of your bom project
    version = "someVersion"// The version of the artifact created
}
```
legacy plugin application:
```kotlin
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("dev.thiagosouto:bom-plugin:0.3")
    }
}

apply(plugin = "dev.thiagosouto.plugins.bom-plugin")

bomMetadata{
    artifactId = "some-artifact-id" // The id of the artifact(pom.xml) created
    description = "some description" // artifact description
    groupId = "some.group.id" //  The id for the project group.
    name = "some-name" // Name of your bom project
    version = "someVersion"// The version of the artifact created
}
```
#### Using groovy
plugin-DSL:

```groovy
plugins {
    id "dev.thiagosouto.plugins.bom-plugin" version "0.3"
}
bomMetadata{
    artifactId = "some-artifact-id" // The id of the artifact(pom.xml) created
    description = "some description" // artifact description
    groupId = "some.group.id" //  The id for the project group.
    name = "some-name" // Name of your bom project
    version = "someVersion"// The version of the artifact created
}
```
legacy plugin application:
```groovy
buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "dev.thiagosouto:bom-plugin:0.3"
    }
}

apply plugin: "dev.thiagosouto.plugins.bom-plugin"
bomMetadata{
    artifactId = "some-artifact-id" // The id of the artifact(pom.xml) created
    description = "some description" // artifact description
    groupId = "some.group.id" //  The id for the project group.
    name = "some-name" // Name of your bom project
    version = "someVersion"// The version of the artifact created
}
```

### Tasks
`./gradlew createBomFile` will create the `pom.xml` in `build/outputs/bom/pom.xml`
`./gradlew createBomClass` will create the kotlin classes that represents the `pom.xml`. They will be placed in `build/outputs/bom/some/group/id`

License
=======
MIT License

        Copyright (c) 2021 Thiago Souto

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
