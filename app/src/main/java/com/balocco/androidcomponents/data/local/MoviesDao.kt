package com.balocco.androidcomponents.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>): Completable

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    fun queryAllMovies(): Flowable<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} ORDER BY ${Movie.COLUMN_VOTE_AVERAGE} DESC")
    fun queryAllMoviesSortedByRating(): Flowable<List<Movie>>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE ${Movie.COLUMN_ID} = :id")
    fun queryMovieWithId(id: String): Single<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<Genre>): Completable

    @Query("SELECT * FROM ${Genre.TABLE_NAME} WHERE ${Genre.COLUMN_ID} IN (:ids)")
    fun queryGenres(ids: List<Int>): Flowable<List<Genre>>

    companion object {
        const val DATABASE_NAME = "moviesDatabase"
    }
}