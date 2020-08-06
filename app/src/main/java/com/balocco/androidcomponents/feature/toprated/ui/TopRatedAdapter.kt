package com.balocco.androidcomponents.feature.toprated.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.data.model.Movie
import com.balocco.androidcomponents.data.remote.ImageLoader
import java.util.*
import javax.inject.Inject

class TopRatedAdapter @Inject constructor(
    context: Context,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: MutableList<Movie> = ArrayList()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun update(newValues: List<Movie>) {
        movies.clear()
        movies.addAll(newValues)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = 0

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.listitem_movie, parent, false)
        return TopRatedViewHolder(
            view,
            imageLoader
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as TopRatedViewHolder
        itemViewHolder.onBindViewHolder(movies[position])
    }
}