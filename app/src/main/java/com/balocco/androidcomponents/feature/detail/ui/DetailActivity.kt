package com.balocco.androidcomponents.feature.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.di.AppComponent
import com.google.android.material.appbar.CollapsingToolbarLayout

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

class DetailActivity : BaseActivity() {

    private lateinit var collapsingToolbar: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        collapsingToolbar = findViewById(R.id.collapsing_toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary))
        collapsingToolbar.title = ""
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

    companion object {
        fun newIntent(context: Context, movieId: String): Intent? {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            return intent
        }

    }
}