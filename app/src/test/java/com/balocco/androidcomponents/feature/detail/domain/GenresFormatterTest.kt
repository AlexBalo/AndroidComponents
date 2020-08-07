package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenresFormatterTest {

    private lateinit var formatter: GenresFormatter

    @Before
    fun setUp() {
        formatter = GenresFormatter()
    }

    @Test
    fun `When formatting empty list of genres, returns empty string`() {
        val formattedResult = formatter.toList(emptyList())

        assertEquals("", formattedResult)
    }

    @Test
    fun `When formatting list with single enty, returns genre`() {
        val genre = Genre(1, "Action")
        val formattedResult = formatter.toList(listOf(genre))

        assertEquals("Action", formattedResult)
    }

    @Test
    fun `When formatting list with multiple enties, returns genres comma separated`() {
        val genre1 = Genre(1, "Action")
        val genre2 = Genre(2, "Adventure")
        val formattedResult = formatter.toList(listOf(genre1, genre2))

        assertEquals("Action, Adventure", formattedResult)
    }

}