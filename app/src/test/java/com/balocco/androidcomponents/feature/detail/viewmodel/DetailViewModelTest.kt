package com.balocco.androidcomponents.feature.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.common.scheduler.TestSchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.feature.detail.domain.FetchGenresUseCase
import com.balocco.androidcomponents.feature.detail.domain.GenresFormatter
import com.balocco.androidcomponents.feature.detail.domain.LoadGenresUseCase
import com.balocco.androidcomponents.feature.detail.domain.LoadMovieUseCase
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Captor private lateinit var movieCaptor: ArgumentCaptor<DetailState>
    @Mock private lateinit var observer: Observer<DetailState>
    @Mock private lateinit var loadMoviesUseCase: LoadMovieUseCase
    @Mock private lateinit var fetchGenresUseCase: FetchGenresUseCase
    @Mock private lateinit var loadGenresUseCase: LoadGenresUseCase
    @Mock private lateinit var genresFormatter: GenresFormatter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            DetailViewModel(
                TestSchedulerProvider(),
                loadMoviesUseCase,
                fetchGenresUseCase,
                loadGenresUseCase,
                genresFormatter
            )
        viewModel.detailState().observeForever(observer)
    }

    @Test
    fun `When started with movie id and successful, notifies success`() {
        val genre = Genre(1, "Action")
        val movie = TestUtils.createMovie(id = "id", genres = mutableListOf(1))
        whenever(loadMoviesUseCase("id")).thenReturn(Single.just(movie))
        whenever(loadGenresUseCase(movie.genres)).thenReturn(Flowable.just(listOf(genre)))

        viewModel.start("id")

        verify(observer).onChanged(movieCaptor.capture())
        val state = movieCaptor.value
        assertEquals(State.SUCCESS, state.state)
        assertEquals(state.movie, movie)
        assertEquals(0, state.errorMessage)
    }

    @Test
    fun `When started with movie id and successful local genres available, notifies success`() {
        val genres = listOf(Genre(1, "Action"))
        val movie = TestUtils.createMovie(id = "id", genres = mutableListOf(1))
        whenever(loadMoviesUseCase("id")).thenReturn(Single.just(movie))
        whenever(loadGenresUseCase(movie.genres)).thenReturn(Flowable.just(genres))
        whenever(genresFormatter.toList(genres)).thenReturn("Action")

        viewModel.start("id")

        // Invoked twice when results from genres are received from local storage
        verify(observer, times(2)).onChanged(movieCaptor.capture())
        val state = movieCaptor.value
        assertEquals(State.SUCCESS, state.state)
        assertEquals(state.movie, movie)
        assertEquals(state.genres, "Action")
        assertEquals(0, state.errorMessage)
    }

    @Test
    fun `When started with movie id and successful local genres not available, loads genres`() {
        val movie = TestUtils.createMovie(id = "id", genres = mutableListOf(1))
        whenever(loadMoviesUseCase("id")).thenReturn(Single.just(movie))
        whenever(loadGenresUseCase(movie.genres)).thenReturn(Flowable.just(emptyList()))
        whenever(fetchGenresUseCase()).thenReturn(Completable.complete())

        viewModel.start("id")

        verify(fetchGenresUseCase)()
    }

    @Test
    fun `When started with movie id and failure, notifies error`() {
        whenever(loadMoviesUseCase("id")).thenReturn(Single.error(Throwable()))

        viewModel.start("id")

        verify(observer).onChanged(movieCaptor.capture())
        val state = movieCaptor.value
        assertEquals(State.ERROR, state.state)
        assertNull(state.movie)
        assertEquals(R.string.error_loading_movie, state.errorMessage)
    }
}