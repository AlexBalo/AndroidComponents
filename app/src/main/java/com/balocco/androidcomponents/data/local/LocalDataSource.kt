package com.balocco.androidcomponents.data.local

import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun insertMovies(movies: List<Movie>): Completable

    fun fetchAllMovies(): Flowable<List<Movie>>

    fun fetchAllMoviesSortedByRating(): Flowable<List<Movie>>

    fun fetchMovie(id: String): Single<Movie>

    fun insertGenres(genres: List<Genre>): Completable

    fun fetchGenres(ids: List<Int>): Flowable<List<Genre>>

}