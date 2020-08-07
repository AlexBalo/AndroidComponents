package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.Genre
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadGenresUseCaseTest {

    @Mock lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: LoadGenresUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = LoadGenresUseCase(moviesRepository)
    }

    @Test
    fun `When loading genres ids, returns genres from local data source`() {
        val genre = Genre(1, "Action")
        whenever(moviesRepository.loadGenres(listOf(1))).thenReturn(Flowable.just(listOf(genre)))

        useCase(listOf(1)).test().assertResult(listOf(genre))
    }
}