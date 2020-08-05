package com.balocco.androidcomponents.feature.main.di

import androidx.lifecycle.ViewModel
import com.balocco.androidcomponents.di.ViewModelKey
import com.balocco.androidcomponents.feature.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module()
abstract class MainSubcomponentsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}