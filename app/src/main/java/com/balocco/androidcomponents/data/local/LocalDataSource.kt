package com.balocco.androidcomponents.data.local

import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun insertMovies(movies: List<Movie>): Completable

    fun fetchAllMovies(): Single<List<Movie>>

}