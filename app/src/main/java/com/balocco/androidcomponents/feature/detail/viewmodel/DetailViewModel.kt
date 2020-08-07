package com.balocco.androidcomponents.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidcomponents.common.viewmodel.BaseViewModel
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.feature.detail.domain.FetchGenresUseCase
import com.balocco.androidcomponents.feature.detail.domain.GenresFormatter
import com.balocco.androidcomponents.feature.detail.domain.LoadGenresUseCase
import com.balocco.androidcomponents.feature.detail.domain.LoadMovieUseCase
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val loadMovieUseCase: LoadMovieUseCase,
    private val fetchGenresUseCase: FetchGenresUseCase,
    private val loadGenresUseCase: LoadGenresUseCase,
    private val genresFormatter: GenresFormatter
) : BaseViewModel() {

    private var detailState: MutableLiveData<DetailState> = MutableLiveData()
    private var movie: Movie? = null
    private var genres: String = ""

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
        this.movie = movie
        detailState.value = DetailState(State.SUCCESS, movie, genres)

        loadGenresUseCase(movie.genres)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { genres -> handleGenresResult(genres) },
                { error -> { /* Handle failure in fetching genres, for now we can ignore */ } }
            ).addTo(compositeDisposable)
    }

    private fun handleGenresResult(genres: List<Genre>) {
        // TODO: we could improve by comparing number of received genres locally with movie genres
        if (genres.isEmpty()) {
            fetchGenresUseCase()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe().addTo(compositeDisposable)
            return
        }

        this.genres = genresFormatter.toList(genres)
        detailState.value = DetailState(State.SUCCESS, movie, this.genres)
    }

    private fun handleError() {
        detailState.value = DetailState(
            state = State.ERROR,
            movie = movie,
            genres = genres,
            errorMessage = R.string.error_loading_movie
        )
    }
}