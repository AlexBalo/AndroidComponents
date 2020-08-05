package com.balocco.androidcomponents.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.balocco.androidcomponents.AndroidComponentsApplication
import com.balocco.androidcomponents.di.AppComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject((applicationContext as AndroidComponentsApplication).appComponent)
        super.onCreate(savedInstanceState)
    }

    abstract fun onInject(appComponent: AppComponent)
}