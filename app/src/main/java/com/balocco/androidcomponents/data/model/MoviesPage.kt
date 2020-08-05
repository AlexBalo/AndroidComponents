package com.balocco.androidcomponents.data.model

import com.google.gson.annotations.SerializedName

data class MoviesPage(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<Movie>
)