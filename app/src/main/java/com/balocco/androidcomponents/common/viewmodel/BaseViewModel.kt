package com.balocco.androidcomponents.common.viewmodel

import androidx.lifecycle.ViewModel
import com.balocco.androidcomponents.common.navigation.Navigator
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    internal var navigator: Navigator? = null
    internal val compositeDisposable = CompositeDisposable()

    fun setNavigator(navigator: Navigator?) {
        this.navigator = navigator;
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}