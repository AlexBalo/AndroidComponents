package com.balocco.androidcomponents

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.balocco.androidcomponents.data.local.AppDatabase
import com.balocco.androidcomponents.data.local.MoviesDao
import com.balocco.androidcomponents.data.model.Movie
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MoviesDaoTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var dao: MoviesDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun fetchAllMovies_returnsAllMoviesInDatabase() {
        val genresOne = mutableListOf(1, 3)
        val movieOne = givenMovie(id = "1", title = "Simple title", genres = genresOne)
        val genresTwo = mutableListOf(4, 6)
        val movieTwo = givenMovie(id = "2", title = "Another title", genres = genresTwo)
        dao.insertMovies(listOf(movieOne, movieTwo)).test()

        dao.queryAllMovies().test().assertValues(listOf(movieOne, movieTwo))
    }

    @Test
    fun fetchAllMoviesSortedByRating_returnsAllMoviesSortedByRatingDescending() {
        val genresOne = mutableListOf(1, 3)
        val movieOne =
            givenMovie(id = "1", title = "Simple title", voteAverage = 8.3, genres = genresOne)
        val genresTwo = mutableListOf(4, 6)
        val movieTwo =
            givenMovie(id = "2", title = "Another title", voteAverage = 9.2, genres = genresTwo)
        dao.insertMovies(listOf(movieOne, movieTwo)).test()

        dao.queryAllMoviesSortedByRating().test().assertValue(listOf(movieTwo, movieOne))
    }

    private fun givenMovie(
        id: String,
        title: String,
        voteAverage: Double = 8.0,
        genres: List<Int>
    ): Movie {
        return Movie(
            id = id,
            title = title,
            overview = "Simple overview",
            originalLanguage = "en",
            voteAverage = voteAverage,
            releaseDate = "10-15-2020",
            backdropImageName = "image.png",
            posterImageName = "image.png",
            genres = genres,
            voteCount = 10,
            popularity = 120.0
        )
    }
}