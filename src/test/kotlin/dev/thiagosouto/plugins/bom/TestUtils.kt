package dev.thiagosouto.plugins.bom

import org.w3c.dom.Node
import java.io.File
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class TestUtils {

    companion object {
        fun getNodeString(element: Node, fileName: String): String {
            val path = "${fileName}.xml"
            createXmlNode(element, path)
            val file = File(path)
            val content = file.readText()
            file.delete()
            return content
        }

        private fun createXmlNode(element: Node, fileName: String) {
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            transformer.transform(DOMSource(element), StreamResult(File(fileName)))
        }
    }
}