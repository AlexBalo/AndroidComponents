package com.balocco.androidcomponents

import com.balocco.androidcomponents.data.model.Movie

object TestUtils {

    fun createMovie(
        id: String,
        genres: List<Int> = listOf()
    ): Movie =
        Movie(
            id = id,
            title = "Title",
            overview = "Overview",
            originalLanguage = "en",
            voteAverage = 19.0,
            releaseDate = "11-10-2020",
            backdropImageName = "backdrop.jpg",
            posterImageName = "poster.jpg",
            genres = genres,
            voteCount = 100,
            popularity = 123.0
        )
}