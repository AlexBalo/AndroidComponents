package com.balocco.androidcomponents.feature.toprated.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

private const val PAGE_SIZE = 10

class TopRatedPaginatorTest {

    private lateinit var paginator: TopRatedPaginator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        paginator = TopRatedPaginator(PAGE_SIZE)
    }

    @Test
    fun `When queries for pagination and page to load higher than total, returns false`() {
        paginator.updatePages(MoviesPageInfo(page = 5, totalPages = 5))

        val currentLastIndex = 5 * PAGE_SIZE
        val canPaginate = paginator.canPaginate(currentLastIndex)

        assertFalse(canPaginate)
    }

    @Test
    fun `When queries for pagination and page to load higher than current, returns true`() {
        paginator.updatePages(MoviesPageInfo(page = 4, totalPages = 5))

        val currentLastIndex = 4 * PAGE_SIZE
        val canPaginate = paginator.canPaginate(currentLastIndex)

        assertTrue(canPaginate)
    }

    @Test
    fun `When queries for pagination and page to load higher than current, updates current page`() {
        paginator.updatePages(MoviesPageInfo(page = 4, totalPages = 5))

        val currentLastIndex = 4 * PAGE_SIZE
        val previousPage = paginator.nextPage()
        paginator.canPaginate(currentLastIndex)
        val nextPage = paginator.nextPage()

        assertEquals(previousPage, 4)
        assertEquals(nextPage, 5)
    }

    @Test
    fun `When confirmed pagination possible and page error received, restores current page`() {
        paginator.updatePages(MoviesPageInfo(page = 4, totalPages = 5))

        val currentLastIndex = 4 * PAGE_SIZE
        val previousPage = paginator.nextPage()
        paginator.canPaginate(currentLastIndex)
        val nextPage = paginator.nextPage()
        paginator.pageError()
        val newPage = paginator.nextPage()

        assertEquals(4, previousPage)
        assertEquals(5, nextPage)
        assertEquals(previousPage, newPage)
    }

}