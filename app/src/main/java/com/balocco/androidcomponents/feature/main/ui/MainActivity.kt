package com.balocco.androidcomponents.feature.main.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.data.MoviesRepository
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidcomponents.feature.main.viewmodel.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var repository: MoviesRepository

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        repository.fetchTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.e("Success", "" + (result.toString() ?: "No results")) },
                { error -> Log.e("Error", "An error occurred") }
            )
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.mainComponent().create(this).inject(this)
    }
}