package com.balocco.androidcomponents.feature.toprated.di

import android.app.Activity
import com.balocco.androidcomponents.di.ActivityScope
import com.balocco.androidcomponents.feature.toprated.ui.TopRatedActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        TopRatedSubcomponentsModule::class,
        TopRatedViewModelModule::class
    ]
)
interface TopRatedComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity
        ): TopRatedComponent
    }

    fun inject(activity: TopRatedActivity)
}