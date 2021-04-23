package com.example.movies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.databinding.ViewHolderMovieBinding
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
        val binding = ViewHolderMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
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
    private val binding: ViewHolderMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

   @SuppressLint("SetTextI18n")
   fun bind(
       movie: MovieEntity,
       viewModel: MoviesListViewModel
   ) {

       binding.movieTitle.text = movie.title
       binding.releaseDate.text = rlsDate + movie.releaseDate
       binding.reviews.text = movie.reviewCount.toString() + numReviews
       binding.genre.text = movie.genres

       binding.pg.load(if (movie.isAdult) R.drawable.ic_pg_16 else R.drawable.ic_pg_13)

       binding.cardPicture.load(viewModel.baseImageUrl
                      + "original"
                      + (movie.imageUrl ?: movie.detailImageUrl)
       ) {
          placeholder(R.drawable.loading_animation)
       }

       binding.card.setOnClickListener {
           viewModel.onItemClicked(movie.id)
       }
   }

}