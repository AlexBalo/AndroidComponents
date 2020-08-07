package com.balocco.androidcomponents.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres") val genres: List<Genre>
)