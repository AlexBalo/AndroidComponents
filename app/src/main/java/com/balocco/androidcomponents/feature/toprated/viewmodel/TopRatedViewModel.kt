package com.balocco.androidcomponents.feature.toprated.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.RxViewModel
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.toprated.domain.FetchTopRatedMoviesUseCase
import com.balocco.androidcomponents.feature.toprated.domain.LoadTopRatedMoviesUseCase
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class TopRatedViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
    private val loadTopRatedMoviesUseCase: LoadTopRatedMoviesUseCase
) : RxViewModel() {

    private var topRatedState: MutableLiveData<TopRatedState> = MutableLiveData()
    private var movies = mutableListOf<Movie>()

    fun topRatedState(): LiveData<TopRatedState> = topRatedState

    fun start() {
        loadTopRatedMoviesUseCase()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { result -> handleSuccess(result) },
                { handleError() }
            ).addTo(compositeDisposable)
        fetchTopRatedMovies()

    }

    private fun fetchTopRatedMovies() {
        fetchTopRatedMoviesUseCase()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { handleLoading() }
            .subscribe(
                { result -> handlePagination(result) },
                { handleError() }
            ).addTo(compositeDisposable)
    }

    private fun handleLoading() {
        topRatedState.value = TopRatedState(State.LOADING, movies)
    }

    private fun handleSuccess(movies: List<Movie>) {
        this.movies.addAll(movies)
        topRatedState.value = TopRatedState(State.SUCCESS, movies)
    }

    private fun handleError() {
        topRatedState.value = TopRatedState(State.ERROR, movies, R.string.error_loading_movies)
    }

    private fun handlePagination(result: MoviesPage) {
        // Handle pagination events
    }
}