package com.balocco.androidcomponents.feature.main.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.MoviesPage
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FetchTopRatedMoviesUseCaseTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: FetchTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = FetchTopRatedMoviesUseCase(moviesRepository)
    }

    @Test
    fun `When fetching top rated movies, no page specified requests first page from source`() {
        val moviesPage = MoviesPage(
            page = 1,
            totalPages = 10,
            totalResults = 1000,
            results = mutableListOf()
        )
        whenever(moviesRepository.fetchTopRatedMovies()).thenReturn(Single.just(moviesPage))

        useCase().test().assertResult(moviesPage)
    }

    @Test
    fun `When fetching top rated movies, page specified requests specified page from source`() {
        val moviesPage = MoviesPage(
            page = 3,
            totalPages = 10,
            totalResults = 1000,
            results = mutableListOf()
        )
        whenever(moviesRepository.fetchTopRatedMovies(3)).thenReturn(Single.just(moviesPage))

        useCase(3).test().assertResult(moviesPage)
    }
}