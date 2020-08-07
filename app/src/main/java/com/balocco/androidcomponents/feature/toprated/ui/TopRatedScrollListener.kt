package com.balocco.androidcomponents.feature.toprated.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val DEFAULT_THRESHOLD = 1

class TopRatedScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 0
    private var previousTotalItemCount = 0
    private var loading = true

    // Listener for endless scrolling functionality
    private var endlessScrollListener: OnEndlessListListener? = null

    private var verticalScrollPosition = 0

    fun setEndlessScrollListener(endlessScrollListener: OnEndlessListListener?) {
        this.endlessScrollListener = endlessScrollListener
        visibleThreshold = DEFAULT_THRESHOLD
    }

    fun setLoading(isLoading: Boolean) {
        loading = isLoading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        verticalScrollPosition += dy
        if (endlessScrollListener != null) {
            handleEndlessScrollListener()
        }
    }

    private fun handleEndlessScrollListener() {
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
            loading = totalItemCount == 0
        }

        // If it is still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
        }

        // If it is not currently loading, we check to see if we have breached the visibleThreshold and need to reload
        // more data. If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading &&
            totalItemCount - lastVisibleItem <= visibleThreshold
        ) {
            endlessScrollListener?.onLoadMore(totalItemCount)
        }
    }

    interface OnEndlessListListener {
        fun onLoadMore(totalItemCount: Int)
    }
}