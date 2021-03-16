package dev.thiagosouto.plugins.bom

fun String.convertToClassName(): String = formatName().joinToString("", transform = String::capitalize)

fun String.convertToFieldName(): String = replace("-", ".")
    .formatName().mapIndexed { index, string ->
    if (index == 0) {
        string
    } else {
        string.capitalize()
    }
}.joinToString("")

private fun String.formatName() = this.replace("-", "_").split(".")
