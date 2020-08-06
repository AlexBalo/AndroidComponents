package com.balocco.androidcomponents.feature.toprated.viewmodel

import androidx.annotation.StringRes
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie

data class TopRatedState(
    val state: State,
    val results: List<Movie>,
    @StringRes val errorMessage: Int = 0
)