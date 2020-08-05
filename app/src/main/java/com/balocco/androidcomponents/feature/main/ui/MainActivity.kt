package com.balocco.androidcomponents.feature.main.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.common.viewmodel.ViewModelFactory
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidcomponents.feature.main.viewmodel.MainViewModel
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private lateinit var viewModel: MainViewModel
    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        manager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = moviesAdapter
        }

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getMovies().observe(this, Observer { movies -> updateMoviesList(movies) })
        viewModel.start()
    }

    private fun updateMoviesList(movies: List<Movie>) {
        moviesAdapter.update(movies)
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.mainComponent().create(this).inject(this)
    }
}