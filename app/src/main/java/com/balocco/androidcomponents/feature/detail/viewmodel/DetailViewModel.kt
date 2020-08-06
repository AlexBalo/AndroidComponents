package com.balocco.androidcomponents.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.BaseViewModel
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.feature.detail.domain.LoadMovieUseCase
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val loadMovieUseCase: LoadMovieUseCase
) : BaseViewModel() {

    private var detailState: MutableLiveData<DetailState> = MutableLiveData()

    fun detailState(): LiveData<DetailState> = detailState

    fun start(movieId: String) {
        loadMovieUseCase(movieId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { result -> handleMovie(result) },
                { handleError() }
            ).addTo(compositeDisposable)
    }

    private fun handleMovie(movie: Movie) {
        detailState.value = DetailState(State.SUCCESS, movie)
        // Load genres
    }

    private fun handleError() {
        detailState.value = DetailState(
            state = State.ERROR,
            errorMessage = R.string.error_loading_movie
        )
    }
}