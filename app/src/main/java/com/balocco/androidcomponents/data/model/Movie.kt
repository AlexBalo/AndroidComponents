package com.balocco.androidcomponents.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("backdrop_path") val backdropImageName: String,
    @SerializedName("poster_path") val posterImageName: String,
    @SerializedName("genre_ids") val genres: List<Int>,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("popularity") val popularity: Double
)