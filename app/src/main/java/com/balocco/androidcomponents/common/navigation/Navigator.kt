package com.balocco.androidcomponents.common.navigation

import android.app.Activity
import com.balocco.androidcomponents.feature.detail.ui.DetailActivity
import javax.inject.Inject

class Navigator @Inject constructor(
    private val activity: Activity
) {

    fun goToDetail(movieId: String) {
        activity.startActivity(DetailActivity.newIntent(activity, movieId))
    }
}