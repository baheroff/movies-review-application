package com.example.movies.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movies.data.Movie
import com.example.movies.R
import com.example.movies.viewmodels.MoviesListViewModel
import com.google.android.material.card.MaterialCardView

class MoviesAdapter(context: Context,
                    var movies: List<Movie>,
                    private val viewModel: MoviesListViewModel
) : RecyclerView.Adapter<MovieViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    override fun getItemCount(): Int = movies.size

    private fun getItem(position: Int): Movie = movies[position]

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val title: TextView = itemView.findViewById(R.id.movie_title)
    private val duration: TextView = itemView.findViewById(R.id.film_duration)
    private val reviews: TextView = itemView.findViewById(R.id.reviews)
    private val genre: TextView = itemView.findViewById(R.id.genre)
    private val pg: ImageView = itemView.findViewById(R.id.pg)
    private val image: ImageView = itemView.findViewById(R.id.card_picture)

   fun bind(movie: Movie, viewModel: MoviesListViewModel) {
       title.text = movie.title
       duration.text = context.getString(R.string.movie_time, movie.runningTime)
       reviews.text = context.getString(R.string.movie_num_reviews, movie.reviewCount)
       genre.text = movie.genres.joinToString {
           it.name
       }

       Glide.with(context)
               .load(movie.pgAge)
               .into(pg)

       Glide.with(context)
               .load(movie.imageUrl)
               .apply(RequestOptions()
                            .placeholder(R.drawable.loading_animation))
               .into(image)

       itemView.findViewById<MaterialCardView>(R.id.card).setOnClickListener {
           viewModel.onItemClicked(movie)
       }
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context