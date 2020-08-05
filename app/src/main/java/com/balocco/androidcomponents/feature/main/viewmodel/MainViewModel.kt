package com.balocco.androidcomponents.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.RxViewModel
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.feature.main.domain.FetchTopRatedMoviesUseCase
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase
) : RxViewModel() {

    private var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies(): LiveData<List<Movie>> = movies

    fun start() {
        fetchTopRatedMoviesUseCase()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { result -> handleResults(result) },
                { error -> Log.e("Error", "An error occurred") }
            ).addTo(compositeDisposable)
    }

    private fun handleResults(moviesPage: MoviesPage) {
        movies.value = moviesPage.results
    }
}