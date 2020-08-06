package com.balocco.androidcomponents.feature.toprated.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.MoviesPage
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchTopRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(page: Int = 1): Single<MoviesPage> = repository.fetchTopRatedMovies(page)
}