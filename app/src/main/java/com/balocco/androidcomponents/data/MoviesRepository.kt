package com.balocco.androidcomponents.data

import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.data.remote.RemoteDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    fun fetchTopRatedMovies(page: Int = 1): Single<MoviesPage> =
        remoteDataSource.fetchTopRatedMovies(page)
}