package com.example.movies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.entities.MovieEntity
import com.example.movies.databinding.ViewHolderMovieBinding
import com.example.movies.viewmodels.MoviesListViewModel

class MoviesAdapter(
    private var movies: List<MovieEntity>,
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

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), viewModel)
    }

    override fun onFailedToRecycleView(holder: MovieViewHolder): Boolean = true

    override fun getItemCount(): Int = movies.size

    private fun getItem(position: Int): MovieEntity = movies[position]

    fun bindMovies(newMovies: List<MovieEntity>) {
        movies = newMovies
    }

}

const val RELEASE_DATE_STRING = "Release date: "
const val NUM_REVIEWS_STRING = " REVIEWS"

class MovieViewHolder(
    private val binding: ViewHolderMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val starViews = arrayOf(
        binding.star1,
        binding.star2,
        binding.star3,
        binding.star4,
        binding.star5,
    )

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

        for (count in starViews.indices) {
            if (count < movie.rating) {
                starViews[count].load(R.drawable.ic_star_icon)
            } else {
                starViews[count].load(R.drawable.ic_star_icon_dark)
            }
        }

        binding.cardPicture.load(
            viewModel.baseImageUrl
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