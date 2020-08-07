package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Genres
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FetchGenresUseCaseTest {

    @Mock lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: FetchGenresUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = FetchGenresUseCase(moviesRepository)
    }

    @Test
    fun `When fetching genres successfully, stores genres in repository`() {
        val genres = listOf(Genre(1, "Action"))
        val genresResponse = Genres(genres)
        whenever(moviesRepository.fetchGenres()).thenReturn(Single.just(genresResponse))
        whenever(moviesRepository.storeGenres(genres)).thenReturn(Completable.complete())

        useCase().test().assertComplete()

        verify(moviesRepository).storeGenres(genres)
    }

    @Test
    fun `When fetching genres with error, notify complete and does not store genres`() {
        whenever(moviesRepository.fetchGenres()).thenReturn(Single.error(Throwable()))

        useCase().test().assertComplete()

        verify(moviesRepository, never()).storeGenres(any())
    }
}