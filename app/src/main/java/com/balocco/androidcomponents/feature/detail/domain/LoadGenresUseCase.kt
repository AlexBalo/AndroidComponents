package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.model.Genre
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LoadGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(ids: List<Int>): Flowable<List<Genre>> = repository.loadGenres(ids)

}