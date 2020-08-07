package com.balocco.androidcomponents.data

import com.balocco.androidcomponents.data.local.LocalDataSource
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Genres
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.data.remote.RemoteDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun loadMovie(id: String): Single<Movie> = localDataSource.fetchMovie(id)

    fun loadTopRatedMovies(): Flowable<List<Movie>> = localDataSource.fetchAllMoviesSortedByRating()

    fun storeTopRatedMovies(movies: List<Movie>): Completable = localDataSource.insertMovies(movies)

    fun fetchTopRatedMovies(page: Int = 1): Single<MoviesPage> =
        remoteDataSource.fetchTopRatedMovies(page)

    fun loadGenres(ids: List<Int>): Flowable<List<Genre>> = localDataSource.fetchGenres(ids)

    fun storeGenres(genres: List<Genre>): Completable = localDataSource.insertGenres(genres)

    fun fetchGenres(): Single<Genres> = remoteDataSource.fetchGenres()

}