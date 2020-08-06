package com.balocco.androidcomponents.feature.detail.viewmodel

import androidx.annotation.StringRes
import com.balocco.androidcomponents.common.viewmodel.State
import com.balocco.androidcomponents.data.model.Movie

data class DetailState(
    val state: State,
    val movie: Movie? = null,
    @StringRes val errorMessage: Int = 0
)