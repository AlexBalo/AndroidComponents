package com.balocco.androidcomponents.di

import android.content.Context
import androidx.room.Room
import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.local.AppDatabase
import com.balocco.androidcomponents.data.local.LocalDataSource
import com.balocco.androidcomponents.data.local.MoviesDao
import com.balocco.androidcomponents.data.local.MoviesLocalDataSourceImpl
import com.balocco.androidcomponents.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides

/* Module that contains dependencies for data accessing. */
@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, MoviesDao.DATABASE_NAME)
            .build()

    @Provides
    @ApplicationScope
    fun provideLocalDataSource(database: AppDatabase): LocalDataSource =
        MoviesLocalDataSourceImpl(database.moviesDao())

    @Provides
    @ApplicationScope
    fun provideMoviesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MoviesRepository = MoviesRepository(localDataSource, remoteDataSource)
}