package com.balocco.androidcomponents.feature.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidcomponents.common.scheduler.TestSchedulerProvider
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.main.domain.FetchTopRatedMoviesUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Captor
    private lateinit var moviesCaptor: ArgumentCaptor<List<Movie>>
    @Mock
    private lateinit var observer: Observer<List<Movie>>
    @Mock
    private lateinit var fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(TestSchedulerProvider(), fetchTopRatedMoviesUseCase)
        viewModel.getMovies().observeForever(observer)
    }

    @Test
    fun `When started and movies fetched successfully, updates observers`() {
        val movies = mutableListOf(createMovie("11"), createMovie("12"))
        val moviesPage = createMoviePage(movies)
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.just(moviesPage))

        viewModel.start()

        verify(observer).onChanged(moviesCaptor.capture())
        assertEquals(movies, moviesCaptor.value)
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