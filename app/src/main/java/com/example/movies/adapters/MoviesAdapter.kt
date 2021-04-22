package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.viewmodels.MoviesListViewModel
import com.google.android.material.card.MaterialCardView

class MoviesAdapter(
    var movies: List<MovieEntity>,
    private val viewModel: MoviesListViewModel
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onFailedToRecycleView(holder: MovieViewHolder): Boolean {
        return true
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), viewModel)
    }

    override fun getItemCount(): Int = movies.size

    private fun getItem(position: Int): MovieEntity = movies[position]

}

const val rlsDate = "Release date: "
const val numReviews = " REVIEWS"

class MovieViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val title: TextView = itemView.findViewById(R.id.movie_title)
    private val releaseDate: TextView = itemView.findViewById(R.id.release_date)
    private val reviews: TextView = itemView.findViewById(R.id.reviews)
    private val genre: TextView = itemView.findViewById(R.id.genre)
    private val pg: ImageView = itemView.findViewById(R.id.pg)
    private val image: ImageView = itemView.findViewById(R.id.card_picture)

   fun bind(
       movie: MovieEntity,
       viewModel: MoviesListViewModel
   ) {

       title.text = movie.title
       releaseDate.text = rlsDate + movie.releaseDate//context.getString(R.string.release_date, movie.releaseDate)
       reviews.text = movie.reviewCount.toString() + numReviews //context.getString(R.string.movie_num_reviews, movie.reviewCount)
       genre.text = movie.genres

       pg.load(if (movie.isAdult) R.drawable.ic_pg_16 else R.drawable.ic_pg_13)

       image.load(viewModel.baseImageUrl
                      + "original"
                      + (movie.imageUrl ?: movie.detailImageUrl)
       ) {
           placeholder(R.drawable.loading_animation)
       }

       itemView.findViewById<MaterialCardView>(R.id.card).setOnClickListener {
           viewModel.onItemClicked(movie.id)
       }
   }

}