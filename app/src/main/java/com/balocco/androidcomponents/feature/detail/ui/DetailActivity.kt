package com.balocco.androidcomponents.feature.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.common.viewmodel.ViewModelFactory
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.remote.ImageLoader
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidcomponents.feature.detail.viewmodel.DetailState
import com.balocco.androidcomponents.feature.detail.viewmodel.DetailViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.util.*
import javax.inject.Inject

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

class DetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var backdropImage: ImageView
    private lateinit var voteAverageText: TextView
    private lateinit var voteCountText: TextView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var overviewText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var originalLanguageText: TextView
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        backdropImage = findViewById(R.id.backdrop)
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        voteAverageText = findViewById(R.id.vote)
        voteCountText = findViewById(R.id.vote_count)
        overviewText = findViewById(R.id.overview)
        releaseDateText = findViewById(R.id.release_date)
        originalLanguageText = findViewById(R.id.original_language)

        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary))
        collapsingToolbar.title = ""

        val movieId = intent.extras?.getString(KEY_MOVIE_ID) ?: ""
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.detailState().observe(this, Observer { state -> handleState(state) })
        viewModel.start(movieId)
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.detailComponent().create(this).inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleState(state: DetailState) {
        when (state.state) {
            State.LOADING -> {
            }
            State.SUCCESS -> populateView(state.movie)
            State.ERROR -> {
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun populateView(movie: Movie?) {
        movie?.let {
            imageLoader.loadImage(backdropImage, movie.backdropImageName)
            collapsingToolbar.title = movie.title
            voteAverageText.text = getString(R.string.vote, movie.voteAverage.toString())
            voteCountText.text = getString(R.string.vote_count, movie.voteCount.toString())
            overviewText.text = movie.overview
            releaseDateText.text = movie.releaseDate
            originalLanguageText.text = movie.originalLanguage.toUpperCase(Locale.ENGLISH)
        }
    }

    companion object {
        fun newIntent(context: Context, movieId: String): Intent? {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            return intent
        }

    }
}