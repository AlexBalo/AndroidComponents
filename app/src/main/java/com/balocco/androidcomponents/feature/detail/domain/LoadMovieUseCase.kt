package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(id: String): Single<Movie> = repository.loadMovie(id)

}