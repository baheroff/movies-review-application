package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.databinding.ViewpagerHolderBinding
import com.example.movies.viewmodels.MoviesListViewModel

class ViewPagerAdapter(
    private var movies: List<MovieEntity>,
    private val viewModel: MoviesListViewModel
) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        val binding = ViewpagerHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding, movies, viewModel)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = getItem(position).toString()
        holder.bind(movies.filter { it.category == item }, viewModel)
    }

    override fun getItemCount(): Int = MoviesCategories.values().size

    private fun getItem(position: Int): MoviesCategories = MoviesCategories.values()[position]
}

class PagerViewHolder(
    private val binding: ViewpagerHolderBinding,
    movies: List<MovieEntity>,
    viewModel: MoviesListViewModel
) : RecyclerView.ViewHolder(binding.root) {

   // private val adapter = MoviesAdapter(movies, viewModel)

    init {
        binding.refreshContainer.setProgressBackgroundColorSchemeResource(R.color.highlight_red_color)
    }

    fun bind(
        movies: List<MovieEntity>,
        viewModel: MoviesListViewModel
    ) {
        binding.moviesList.adapter = MoviesAdapter(movies, viewModel)
        //adapter.movies = movies
        //adapter.notifyDataSetChanged()
        binding.refreshContainer.setOnRefreshListener {
            viewModel.reload()
        }
    }
}