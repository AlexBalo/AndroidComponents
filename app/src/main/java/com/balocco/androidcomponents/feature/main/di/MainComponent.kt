package com.balocco.androidcomponents.feature.main.di

import android.app.Activity
import com.balocco.androidcomponents.di.ActivityScope
import com.balocco.androidcomponents.feature.main.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity
        ): MainComponent
    }

    fun inject(activity: MainActivity)
}