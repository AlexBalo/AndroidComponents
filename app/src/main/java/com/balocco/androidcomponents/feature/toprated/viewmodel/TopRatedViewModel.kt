package com.balocco.androidcomponents.feature.toprated.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.BaseViewModel
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.toprated.domain.FetchTopRatedMoviesUseCase
import com.balocco.androidcomponents.feature.toprated.domain.LoadTopRatedMoviesUseCase
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class TopRatedViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val topRatedPaginator: TopRatedPaginator,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
    private val loadTopRatedMoviesUseCase: LoadTopRatedMoviesUseCase
) : BaseViewModel() {

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
    }

    fun onMovieSelected(movie: Movie) {
        navigator?.goToDetail(movie.id)
    }

    fun onListScrolled(index: Int) {
        if (topRatedPaginator.canPaginate(index)) {
            fetchTopRatedMovies(topRatedPaginator.nextPage())
        }
    }

    private fun fetchTopRatedMovies(page: Int = 1) {
        fetchTopRatedMoviesUseCase(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { handleLoading() }
            .subscribe(
                { result -> handlePagination(result) },
                { handlePaginationError() }
            ).addTo(compositeDisposable)
    }

    private fun handlePaginationError() {
        topRatedPaginator.pageError()
        handleError()
    }

    private fun handleLoading() {
        topRatedState.value = TopRatedState(State.LOADING, movies)
    }

    private fun handleSuccess(movies: List<Movie>) {
        if (movies.isEmpty()) {
            fetchTopRatedMovies(1)
            return
        }

        this.movies.clear()
        this.movies.addAll(movies)
        topRatedState.value = TopRatedState(State.SUCCESS, movies)
    }

    private fun handleError() {
        topRatedState.value = TopRatedState(State.ERROR, movies, R.string.error_loading_movies)
    }

    private fun handlePagination(result: MoviesPage) {
        topRatedPaginator.updatePages(MoviesPageInfo(result.page, result.totalPages))
    }
}