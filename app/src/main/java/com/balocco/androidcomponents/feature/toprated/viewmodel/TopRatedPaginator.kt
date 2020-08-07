package com.balocco.androidcomponents.feature.toprated.viewmodel

import javax.inject.Inject

class TopRatedPaginator @Inject constructor(
    private val pageSize: Int
) {

    private var currentPage = 0
    private var totalPages = 0;

    fun canPaginate(index: Int): Boolean {
        val nextPageToLoad = (index / pageSize) + 1
        if (nextPageToLoad > totalPages) {
            return false
        }

        if (nextPageToLoad > currentPage) {
            currentPage = nextPageToLoad
            return true
        }
        return false
    }

    fun nextPage() = currentPage

    fun pageError() {
        currentPage--
    }

    fun updatePages(moviesPageInfo: MoviesPageInfo) {
        currentPage = moviesPageInfo.page
        totalPages = moviesPageInfo.totalPages
    }
}