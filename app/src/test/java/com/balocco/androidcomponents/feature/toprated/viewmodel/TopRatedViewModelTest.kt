package com.balocco.androidcomponents.feature.toprated.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.scheduler.TestSchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.toprated.domain.FetchTopRatedMoviesUseCase
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TopRatedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TopRatedViewModel

    @Captor
    private lateinit var moviesCaptor: ArgumentCaptor<TopRatedState>

    @Mock
    private lateinit var observer: Observer<TopRatedState>

    @Mock
    private lateinit var fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            TopRatedViewModel(
                TestSchedulerProvider(),
                fetchTopRatedMoviesUseCase
            )
        viewModel.topRatedState().observeForever(observer)
    }

    @Test
    fun `When started and fetching movies, updates observers with loading state`() {
        val moviesPage = createMoviePage(mutableListOf())
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.just(moviesPage))

        viewModel.start()

        // We get invokation for the success state as well so we check only first result
        verify(observer, times(2)).onChanged(moviesCaptor.capture())
        val loadingState = moviesCaptor.allValues[0]
        assertEquals(State.LOADING, loadingState.state)
        assertTrue(loadingState.results.isEmpty())
        assertEquals(0, loadingState.errorMessage)
    }

    @Test
    fun `When started and movies fetched successfully, updates observers with success state`() {
        val movies = mutableListOf(createMovie("11"), createMovie("12"))
        val moviesPage = createMoviePage(movies)
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.just(moviesPage))

        viewModel.start()

        // We get invokation for the loading state as well so we check second result
        verify(observer, times(2)).onChanged(moviesCaptor.capture())
        val successState = moviesCaptor.allValues[1]
        assertEquals(State.SUCCESS, successState.state)
        assertEquals(movies, successState.results)
        assertEquals(0, successState.errorMessage)
    }

    @Test
    fun `When started and movies fetched with error, updates observers with error state`() {
        val moviesPage = createMoviePage(mutableListOf())
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.error(Throwable()))

        viewModel.start()

        // We get invokation for the success state as well so we check only first result
        verify(observer, times(2)).onChanged(moviesCaptor.capture())
        val loadingState = moviesCaptor.allValues[1]
        assertEquals(State.ERROR, loadingState.state)
        assertTrue(loadingState.results.isEmpty())
        assertEquals(R.string.error_loading_movies, loadingState.errorMessage)
    }

    private fun createMoviePage(movies: List<Movie>): MoviesPage =
        MoviesPage(
            page = 1,
            totalPages = 10,
            totalResults = 1000,
            results = movies
        )

    private fun createMovie(id: String): Movie =
        Movie(
            id = id,
            title = "Title",
            overview = "Overview",
            originalLanguage = "en",
            voteAverage = 19.0,
            releaseDate = "11-10-2020",
            backdropImageName = "backdrop.jpg",
            posterImageName = "poster.jpg",
            genres = mutableListOf(),
            voteCount = 100,
            popularity = 123.0
        )
}