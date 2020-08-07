package com.balocco.androidcomponents.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID)
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String,
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    @SerializedName("vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropImageName: String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterImageName: String,
    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    val genres: List<Int>,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    val popularity: Double
) {
    companion object {
        const val TABLE_NAME = "movies"
        const val COLUMN_ID = "uid"
        const val COLUMN_VOTE_AVERAGE = "vote_average"
    }
}