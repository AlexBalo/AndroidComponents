package com.balocco.androidcomponents.feature.detail.di

import android.app.Activity
import com.balocco.androidcomponents.di.ActivityScope
import com.balocco.androidcomponents.feature.detail.ui.DetailActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [DetailViewModelModule::class]
)
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity
        ): DetailComponent
    }

    fun inject(activity: DetailActivity)
}