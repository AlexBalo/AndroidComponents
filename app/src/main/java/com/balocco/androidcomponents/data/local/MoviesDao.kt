package com.balocco.androidcomponents.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Insert
    fun insertMovies(movies: List<Movie>): Completable

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun queryAllMovies(): Single<List<Movie>>

    companion object {
        val DATABASE_NAME = "moviesDatabase"
    }

}