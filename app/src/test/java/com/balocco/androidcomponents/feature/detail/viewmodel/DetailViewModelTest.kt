package com.balocco.androidcomponents.feature.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.common.scheduler.TestSchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.feature.detail.domain.LoadMovieUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Captor
    private lateinit var movieCaptor: ArgumentCaptor<DetailState>

    @Mock
    private lateinit var observer: Observer<DetailState>

    @Mock
    private lateinit var loadMoviesUseCase: LoadMovieUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            DetailViewModel(
                TestSchedulerProvider(),
                loadMoviesUseCase
            )
        viewModel.detailState().observeForever(observer)
    }

    @Test
    fun `When started with movie id and successful, notifies success`() {
        val movie = TestUtils.createMovie("id")
        whenever(loadMoviesUseCase("id")).thenReturn(Single.just(movie))

        viewModel.start("id")

        verify(observer).onChanged(movieCaptor.capture())
        val state = movieCaptor.value
        assertEquals(State.SUCCESS, state.state)
        assertEquals(state.movie, movie)
        assertEquals(0, state.errorMessage)
    }

    @Test
    fun `When started with movie id and failure, notifies error`() {
        val movie = TestUtils.createMovie("id")
        whenever(loadMoviesUseCase("id")).thenReturn(Single.error(Throwable()))

        viewModel.start("id")

        verify(observer).onChanged(movieCaptor.capture())
        val state = movieCaptor.value
        assertEquals(State.ERROR, state.state)
        assertNull(state.movie)
        assertEquals(R.string.error_loading_movie, state.errorMessage)
    }
}