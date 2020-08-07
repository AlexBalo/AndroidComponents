package com.balocco.androidcomponents.feature.toprated.domain

import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.MoviesPage
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FetchTopRatedMoviesUseCaseTest {

    @Mock lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: FetchTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase =
            FetchTopRatedMoviesUseCase(
                moviesRepository
            )
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
        whenever(moviesRepository.storeTopRatedMovies(any())).thenReturn(Completable.complete())

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
        whenever(moviesRepository.storeTopRatedMovies(any())).thenReturn(Completable.complete())

        useCase(3).test().assertResult(moviesPage)
    }

    @Test
    fun `When fetching top rated movies, stores results locally`() {
        val movies = mutableListOf(TestUtils.createMovie("1"), TestUtils.createMovie("2"))
        val moviesPage = MoviesPage(
            page = 3,
            totalPages = 10,
            totalResults = 1000,
            results = movies
        )
        whenever(moviesRepository.fetchTopRatedMovies(3)).thenReturn(Single.just(moviesPage))
        whenever(moviesRepository.storeTopRatedMovies(movies)).thenReturn(Completable.complete())

        useCase(3).test().assertResult(moviesPage)

        verify(moviesRepository).storeTopRatedMovies(movies)
    }
}