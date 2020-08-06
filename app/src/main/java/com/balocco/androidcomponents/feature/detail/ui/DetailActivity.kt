package com.balocco.androidcomponents.feature.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.common.ui.BaseActivity
import com.balocco.androidcomponents.di.AppComponent

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.detailComponent().create(this).inject(this)
    }

    companion object {
        fun newIntent(context: Context, movieId: String): Intent? {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            return intent
        }

    }
}