package com.balocco.androidcomponents.feature.toprated.di

import androidx.lifecycle.ViewModel
import com.balocco.androidcomponents.di.ViewModelKey
import com.balocco.androidcomponents.feature.toprated.viewmodel.TopRatedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module()
abstract class TopRatedSubcomponentsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopRatedViewModel::class)
    abstract fun bindMainViewModel(topRatedViewModel: TopRatedViewModel): ViewModel
}