package dev.thiagosouto.plugins.bom

import com.google.common.truth.Truth.assertThat
import dev.thiagosouto.plugins.bom.convertToClassName
import dev.thiagosouto.plugins.bom.convertToFieldName
import org.junit.Test

class StringExtKtTest {

    @Test
    fun `should convert groupId to class name following the kotlin naming`() {
        assertThat("dev.thiagosouto".convertToClassName()).isEqualTo("DevThiagosouto")
    }

    @Test
    fun `should convert hifen to class name following the kotlin naming`() {
        assertThat("dev.thiagosouto-teste".convertToClassName()).isEqualTo("DevThiagosouto_teste")
    }

    @Test
    fun `should convert groupId to field name following the kotlin naming`() {
        assertThat("dev.thiagosouto".convertToFieldName()).isEqualTo("devThiagosouto")
    }

    @Test
    fun `should convert hifen to field name following the kotlin naming`() {
        assertThat("dev.thiagosouto-teste".convertToFieldName()).isEqualTo("devThiagosoutoTeste")
    }

}