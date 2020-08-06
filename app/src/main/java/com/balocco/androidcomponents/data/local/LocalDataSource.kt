package com.balocco.androidcomponents.data.local

import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface LocalDataSource {

    fun insertMovies(movies: List<Movie>): Completable

    fun fetchAllMovies(): Flowable<List<Movie>>

    fun fetchAllMoviesSortedByRating(): Flowable<List<Movie>>

}