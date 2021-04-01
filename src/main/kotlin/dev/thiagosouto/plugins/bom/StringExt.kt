package dev.thiagosouto.plugins.bom

internal fun String.convertToClassName(): String = formatName().joinToString("", transform = String::capitalize)

internal fun String.convertToFieldName(): String = replace("-", ".")
    .formatName().mapIndexed { index, string ->
    if (index == 0) {
        string
    } else {
        string.capitalize()
    }
}.joinToString("")

private fun String.formatName() = this.replace("-", "_").split(".")
