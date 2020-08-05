package com.balocco.androidcomponents

import android.app.Application
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidcomponents.di.DaggerAppComponent

open class AndroidComponentsApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }
}