package com.balocco.androidcomponents.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Genre.TABLE_NAME)
data class Genre(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID)
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "genre"
        const val COLUMN_ID = "uid"
    }
}