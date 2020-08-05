package com.balocco.androidcomponents.feature.main.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balocco.androidcomponents.R
import com.balocco.androidcomponents.data.model.Movie

class MoviesViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val context = itemView.context;
    private val numberText: TextView = itemView.findViewById(R.id.title)
    private val voteText: TextView = itemView.findViewById(R.id.vote)
    private val releaseText: TextView = itemView.findViewById(R.id.release)

    fun onBindViewHolder(movie: Movie) {
        this.numberText.text = movie.title
        this.voteText.text = context.getString(R.string.vote, movie.voteAverage.toString())
        this.releaseText.text = context.getString(R.string.release, movie.releaseDate)
    }
}