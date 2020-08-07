package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.MoviesRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class FetchGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Completable {
        return repository.fetchGenres()
            .onErrorComplete()
            .flatMapCompletable { result ->
                repository.storeGenres(result.genres)
            }
    }
}