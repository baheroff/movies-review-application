package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.databinding.ViewpagerHolderBinding
import com.example.movies.viewmodels.MoviesListViewModel

const val ITEM_MOVIE_TYPE = 0
const val MAX_RECYCLED_VIEWS = 16
const val SPAN_COUNT = 2
const val INITIAL_ITEM_COUNT = 6

class ViewPagerAdapter(
    private var movies: List<MovieEntity>,
    private val viewModel: MoviesListViewModel
) : RecyclerView.Adapter<PagerViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool().apply {
        setMaxRecycledViews(ITEM_MOVIE_TYPE, MAX_RECYCLED_VIEWS)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        val binding = ViewpagerHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val gridLayoutManager = GridLayoutManager(parent.context, SPAN_COUNT).apply {
            initialPrefetchItemCount = INITIAL_ITEM_COUNT
            recycleChildrenOnDetach = true
        }

        binding.recyclerMovies.setRecycledViewPool(viewPool)
        binding.recyclerMovies.setHasFixedSize(true)
        binding.recyclerMovies.layoutManager = gridLayoutManager

        return PagerViewHolder(binding, viewPool, movies, viewModel)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = getItem(position).toString()
        holder.bind(movies.filter { it.category == item }, viewModel)
    }

    override fun onFailedToRecycleView(holder: PagerViewHolder): Boolean = true

    override fun getItemCount(): Int = MoviesCategories.values().size

    private fun getItem(position: Int): MoviesCategories = MoviesCategories.values()[position]
}

class PagerViewHolder(
    binding: ViewpagerHolderBinding,
    viewPool: RecyclerView.RecycledViewPool,
    movies: List<MovieEntity>,
    viewModel: MoviesListViewModel
) : RecyclerView.ViewHolder(binding.root) {

    private val moviesAdapter = MoviesAdapter(movies, viewModel, viewPool)

    init {
        binding.refreshContainer
            .setProgressBackgroundColorSchemeResource(R.color.highlight_red_color)
        binding.refreshContainer.setOnRefreshListener {
            viewModel.reload()
        }
        binding.recyclerMovies.adapter = moviesAdapter
    }

    fun bind(
        movies: List<MovieEntity>,
        viewModel: MoviesListViewModel
    ) {
        moviesAdapter.movies = movies
    }
}