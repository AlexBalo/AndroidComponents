package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.data.MoviesRepository
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadMovieUseCaseTest {

    @Mock lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: LoadMovieUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase =
            LoadMovieUseCase(
                moviesRepository
            )
    }

    @Test
    fun `When loading movie, returns movie from local data source`() {
        val movie = TestUtils.createMovie("1")
        whenever(moviesRepository.loadMovie("1")).thenReturn(Single.just(movie))

        useCase("1").test().assertResult(movie)
    }

}