package com.balocco.androidcomponents.feature.detail.di

import androidx.lifecycle.ViewModel
import com.balocco.androidcomponents.di.ViewModelKey
import com.balocco.androidcomponents.feature.detail.viewmodel.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(topRatedViewModel: DetailViewModel): ViewModel
}