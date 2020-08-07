package com.balocco.androidcomponents.feature.detail.domain

import com.balocco.androidcomponents.data.model.Genre
import javax.inject.Inject

class GenresFormatter @Inject constructor() {

    fun toList(genres: List<Genre>): String {
        val builder = StringBuilder()
        for (genre in genres) {
            if (builder.isNotEmpty()) {
                builder.append(", ")
            }
            builder.append(genre.name)
        }
        return builder.toString()
    }
}