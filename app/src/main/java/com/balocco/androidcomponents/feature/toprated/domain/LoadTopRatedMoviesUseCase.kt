package com.balocco.androidcomponents.feature.toprated.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LoadTopRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flowable<List<Movie>> = repository.loadTopRatedMovies()
}