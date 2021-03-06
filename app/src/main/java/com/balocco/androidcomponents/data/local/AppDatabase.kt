package com.balocco.androidcomponents.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.balocco.androidcomponents.data.model.Genre
import com.balocco.androidcomponents.data.model.Movie

@Database(
    entities = [Movie::class, Genre::class],
    version = 1
)
@TypeConverters(ListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}