package com.balocco.androidcomponents.feature.toprated.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.common.navigation.Navigator
import com.balocco.androidcomponents.common.scheduler.TestSchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.toprated.domain.FetchTopRatedMoviesUseCase
import com.balocco.androidcomponents.feature.toprated.domain.LoadTopRatedMoviesUseCase
import com.nhaarman.mockito_kotlin.*
import io.reactivex.rxjava3.core.Flowable
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

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TopRatedViewModel

    @Captor private lateinit var moviesCaptor: ArgumentCaptor<TopRatedState>
    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var observer: Observer<TopRatedState>
    @Mock private lateinit var topRatedPaginator: TopRatedPaginator
    @Mock private lateinit var fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase
    @Mock private lateinit var loadTopRatedMoviesUseCase: LoadTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            TopRatedViewModel(
                TestSchedulerProvider(),
                topRatedPaginator,
                fetchTopRatedMoviesUseCase,
                loadTopRatedMoviesUseCase
            )
        viewModel.topRatedState().observeForever(observer)
    }

    @Test
    fun `When started no movies locally, updates observers with loading state`() {
        val movies = listOf(TestUtils.createMovie("1"), TestUtils.createMovie("2"))
        val moviesPage = createMoviePage(movies)
        whenever(loadTopRatedMoviesUseCase()).thenReturn(Flowable.just(emptyList()))
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.just(moviesPage))

        viewModel.start()

        verify(observer).onChanged(moviesCaptor.capture())
        val loadingState = moviesCaptor.value
        assertEquals(State.LOADING, loadingState.state)
        assertTrue(loadingState.results.isEmpty())
        assertEquals(0, loadingState.errorMessage)
        verify(fetchTopRatedMoviesUseCase)(1)
    }

    @Test
    fun `When started and movies already available, updates observers with success state`() {
        val movies = mutableListOf(TestUtils.createMovie("11"), TestUtils.createMovie("12"))
        whenever(loadTopRatedMoviesUseCase()).thenReturn(Flowable.just(movies))

        viewModel.start()

        verify(observer, times(1)).onChanged(moviesCaptor.capture())
        val successState = moviesCaptor.value
        assertEquals(State.SUCCESS, successState.state)
        assertEquals(movies, successState.results)
        assertEquals(0, successState.errorMessage)
        verify(fetchTopRatedMoviesUseCase, never())(1)
    }

    @Test
    fun `When started and movies fetched with error, updates observers with error state`() {
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.error(Throwable()))
        whenever(loadTopRatedMoviesUseCase()).thenReturn(Flowable.just(emptyList()))

        viewModel.start()

        // We get invokation for the loading state and error when fetching data
        verify(observer, times(2)).onChanged(moviesCaptor.capture())
        val loadingState = moviesCaptor.allValues[1]
        assertEquals(State.ERROR, loadingState.state)
        assertTrue(loadingState.results.isEmpty())
        assertEquals(R.string.error_loading_movies, loadingState.errorMessage)
    }

    @Test
    fun `When movies fetched with error, notifies paginator`() {
        whenever(fetchTopRatedMoviesUseCase()).thenReturn(Single.error(Throwable()))
        whenever(loadTopRatedMoviesUseCase()).thenReturn(Flowable.just(emptyList()))

        viewModel.start()

        verify(topRatedPaginator).pageError()
    }

    @Test
    fun `When movie selected, navigates to detail view`() {
        val movie = TestUtils.createMovie("12")
        viewModel.setNavigator(navigator)

        viewModel.onMovieSelected(movie)

        verify(navigator).goToDetail(movie.id)
    }

    @Test
    fun `When list scrolled no pagination, does not fetch new page`() {
        whenever(topRatedPaginator.canPaginate(5)).thenReturn(false)

        viewModel.onListScrolled(5)

        verify(fetchTopRatedMoviesUseCase, never())(any())
    }

    @Test
    fun `When list scrolled pagination possible, fetches next page`() {
        val movies = mutableListOf(TestUtils.createMovie("11"), TestUtils.createMovie("12"))
        val moviesPage = createMoviePage(movies)
        whenever(fetchTopRatedMoviesUseCase(6)).thenReturn(Single.just(moviesPage))
        whenever(topRatedPaginator.canPaginate(5)).thenReturn(true)
        whenever(topRatedPaginator.nextPage()).thenReturn(6)

        viewModel.onListScrolled(5)

        verify(fetchTopRatedMoviesUseCase)(6)
    }

    private fun createMoviePage(movies: List<Movie>): MoviesPage =
        MoviesPage(
            page = 1,
            totalPages = 10,
            totalResults = 1000,
            results = movies
        )
}