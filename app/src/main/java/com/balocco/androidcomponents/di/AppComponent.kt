package com.balocco.androidcomponents.di

import android.content.Context
import com.balocco.androidcomponents.AndroidComponentsApplication
import com.balocco.androidcomponents.feature.toprated.di.TopRatedComponent
import dagger.BindsInstance
import dagger.Component

/** Application component refers to application level modules only. */
@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        NetworkModule::class,
        SchedulersModule::class,
        AppSubcomponentsModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    // Activities
    fun mainComponent(): TopRatedComponent.Factory

    fun inject(application: AndroidComponentsApplication)

}