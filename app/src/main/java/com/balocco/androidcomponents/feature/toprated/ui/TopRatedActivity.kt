package com.balocco.androidcomponents.feature.toprated.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.navigation.Navigator
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.common.viewmodel.ViewModelFactory
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidcomponents.feature.toprated.viewmodel.TopRatedState
import com.balocco.androidcomponents.feature.toprated.viewmodel.TopRatedViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class TopRatedActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var topRatedAdapter: TopRatedAdapter

    private lateinit var loading: ProgressBar
    private lateinit var root: ViewGroup
    private lateinit var viewModel: TopRatedViewModel
    private lateinit var manager: LinearLayoutManager
    private lateinit var scrollListener: TopRatedScrollListener

    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toprated_activity)

        root = findViewById(R.id.container)
        loading = findViewById(R.id.loading_spinner)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.title_main)

        manager = LinearLayoutManager(this)
        scrollListener = TopRatedScrollListener(manager)
        scrollListener.setEndlessScrollListener(
            object : TopRatedScrollListener.OnEndlessListListener {
                override fun onLoadMore(totalItemCount: Int) {
                    Log.e("LoadMore", "Index: " + totalItemCount)
                    viewModel.onListScrolled(totalItemCount)
                }
            })
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = topRatedAdapter
            addOnScrollListener(scrollListener)
        }

        topRatedAdapter.setMovieListener(object : TopRatedAdapter.OnMovieSelectedListener {
            override fun onMovieSelected(movie: Movie) {
                viewModel.onMovieSelected(movie)
            }
        })

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(TopRatedViewModel::class.java)
        viewModel.topRatedState().observe(this, Observer { state -> handleState(state) })
        viewModel.start()
        viewModel.setNavigator(navigator)
    }

    override fun onDestroy() {
        viewModel.setNavigator(null)
        topRatedAdapter.setMovieListener(null)
        super.onDestroy()
    }

    private fun handleState(topRatedState: TopRatedState) {
        when (topRatedState.state) {
            State.SUCCESS -> {
                topRatedAdapter.update(topRatedState.results)
                loading.visibility = View.GONE
                scrollListener.setLoading(false)
            }
            State.ERROR -> {
                showError(getString(topRatedState.errorMessage))
                loading.visibility = View.GONE
                scrollListener.setLoading(false)
            }
            State.LOADING -> {
                loading.visibility = View.VISIBLE
                scrollListener.setLoading(true)
            }
        }
    }

    private fun showError(message: String) {
        if (snackbar == null) {
            snackbar = Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
        }

        snackbar?.apply {
            if (!isShown) {
                show()
            }
        }
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.topRatedComponent().create(this).inject(this)
    }
}