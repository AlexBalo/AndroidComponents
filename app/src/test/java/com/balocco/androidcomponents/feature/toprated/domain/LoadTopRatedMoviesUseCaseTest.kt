package com.balocco.androidcomponents.feature.toprated.domain

import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.data.MoviesRepository
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadTopRatedMoviesUseCaseTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: LoadTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase =
            LoadTopRatedMoviesUseCase(
                moviesRepository
            )
    }

    @Test
    fun `When loading top rated movies, returns movies from local data source`() {
        val movies = mutableListOf(TestUtils.createMovie("1"), TestUtils.createMovie("2"))
        whenever(moviesRepository.loadTopRatedMovies()).thenReturn(Flowable.just(movies))

        useCase().test().assertResult(movies)
    }
}