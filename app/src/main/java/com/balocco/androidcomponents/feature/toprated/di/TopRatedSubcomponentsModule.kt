package com.balocco.androidcomponents.feature.toprated.di

import com.balocco.androidcomponents.di.ActivityScope
import com.balocco.androidcomponents.feature.toprated.viewmodel.TopRatedPaginator
import dagger.Module
import dagger.Provides

private const val RESULTS_PER_PAGE = 20

@Module
class TopRatedSubcomponentsModule {

    @Provides
    @ActivityScope
    fun provideTopRatedPaginator(
    ): TopRatedPaginator = TopRatedPaginator(RESULTS_PER_PAGE)

}