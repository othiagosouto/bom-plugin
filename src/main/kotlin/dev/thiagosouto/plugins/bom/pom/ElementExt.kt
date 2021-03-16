package dev.thiagosouto.plugins.bom

import org.w3c.dom.Document
import org.w3c.dom.Element

fun createTagValueElement(document: Document, tagName: String, tagValue: String): Element {
    val element: Element = document.createElement(tagName)
    element.appendChild(document.createTextNode(tagValue))
    return element
}