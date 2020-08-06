package com.balocco.androidcomponents.feature.toprated.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.MoviesPage
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchTopRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(page: Int = 1): Single<MoviesPage> {
        return repository.fetchTopRatedMovies(page)
            .flatMap { result ->
                repository.storeTopRatedMovies(result.results).andThen(Single.just(result))
            }
    }
}