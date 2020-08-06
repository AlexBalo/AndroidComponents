package com.balocco.androidcomponents.data.local

import com.balocco.androidcomponents.data.model.Movie
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MoviesLocalDataSourceImpl(
    private val dao: MoviesDao
) : LocalDataSource {

    override fun insertMovies(movies: List<Movie>): Completable =
        RxJavaBridge.toV3Completable(dao.insertMovies(movies))

    override fun getAllMovies(): Single<List<Movie>> =
        RxJavaBridge.toV3Single(dao.queryAllMovies())

}