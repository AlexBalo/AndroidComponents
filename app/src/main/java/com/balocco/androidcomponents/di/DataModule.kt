package com.balocco.androidcomponents.di

import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides

/* Module that contains dependencies for data accessing. */
@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideMoviesRepository(
        remoteDataSource: RemoteDataSource
    ): MoviesRepository = MoviesRepository(remoteDataSource)
}