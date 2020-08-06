package com.balocco.androidcomponents

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.balocco.androidcomponents.data.local.AppDatabase
import com.balocco.androidcomponents.data.local.MoviesDao
import com.balocco.androidcomponents.data.model.Movie
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BirthdaysDaoTest {

    private lateinit var dao: MoviesDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
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

        dao.queryAllMovies().test().assertValue(listOf(movieOne, movieTwo))
    }

    private fun givenMovie(
        id: String,
        title: String,
        genres: List<Int>
    ): Movie {
        return Movie(
            id = id,
            title = title,
            overview = "Simple overview",
            originalLanguage = "en",
            voteAverage = 8.2,
            releaseDate = "10-15-2020",
            backdropImageName = "image.png",
            posterImageName = "image.png",
            genres = mutableListOf(),
            voteCount = 10,
            popularity = 120.0
        )
    }
}