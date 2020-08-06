package com.balocco.androidcomponents.data.local

import org.junit.Assert.assertEquals
import org.junit.Test

class ListTypeConverterTest {

    private val converter = ListTypeConverter()

    @Test
    fun `When converter receives list of genres to string, converts to list of integer`() {
        assertEquals(converter.fromString("[1,3]"), mutableListOf(1, 3))
    }

    @Test
    fun `When converter receives list of genres to list, converts to gson string`() {
        assertEquals(converter.fromList(mutableListOf(1, 3)), "[1,3]")
    }
}