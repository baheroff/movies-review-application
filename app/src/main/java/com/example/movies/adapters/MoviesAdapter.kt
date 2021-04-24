package com.example.movies.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.databinding.ViewHolderMovieBinding
import com.example.movies.viewmodels.MoviesListViewModel

class MoviesAdapter(
    var movies: List<MovieEntity>,
    private val viewModel: MoviesListViewModel,
    private val viewPool: RecyclerView.RecycledViewPool
) : RecyclerView.Adapter<MovieViewHolder>() {

    // Just for logs
    private var count = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {

        val binding = ViewHolderMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        Log.e("MOVIESVIEWHOLDER CREATED", "${viewType}")
        count++
        Log.e("Count", "${count}")
        return MovieViewHolder(binding)
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        Log.e("PoolSize", "${viewPool.getRecycledViewCount(ITEM_MOVIE_TYPE)} -> ${holder.itemViewType}")
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), viewModel)
    }

    override fun onFailedToRecycleView(holder: MovieViewHolder): Boolean = true

    override fun getItemCount(): Int = movies.size

    private fun getItem(position: Int): MovieEntity = movies[position]

}

const val RELEASE_DATE_STRING = "Release date: "
const val NUM_REVIEWS_STRING = " REVIEWS"

class MovieViewHolder(
    private val binding: ViewHolderMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

   @SuppressLint("SetTextI18n")
   fun bind(
       movie: MovieEntity,
       viewModel: MoviesListViewModel
   ) {
       binding.movieTitle.text = movie.title
       binding.releaseDate.text = RELEASE_DATE_STRING + movie.releaseDate
       binding.reviews.text = movie.reviewCount.toString() + NUM_REVIEWS_STRING
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