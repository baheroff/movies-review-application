package com.example.movies.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.database.entities.MovieEntity
import com.example.movies.databinding.ViewpagerHolderBinding
import com.example.movies.fragments.FragmentMoviesList
import com.example.movies.viewmodels.MoviesListViewModel

private const val ITEM_MOVIE_TYPE = 0
private const val MAX_RECYCLED_VIEWS = 16
private const val INITIAL_ITEM_COUNT = 32
private const val SPAN_COUNT_LANDSCAPE = 4
private const val SPAN_COUNT_PORTRAIT = 2

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

        val spanCount = when (parent.context.resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> SPAN_COUNT_LANDSCAPE
            else -> SPAN_COUNT_PORTRAIT
        }

        val gridLayoutManager = GridLayoutManager(parent.context, spanCount).apply {
            initialPrefetchItemCount = INITIAL_ITEM_COUNT
            recycleChildrenOnDetach = true
        }

        binding.recyclerMovies.apply {
            layoutManager = gridLayoutManager
            setRecycledViewPool(viewPool)
            setHasFixedSize(true)
        }

        return PagerViewHolder(
            binding,
            viewModel,
            parent.findFragment<FragmentMoviesList>().viewLifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = getItem(position).toString()
        holder.bind(
            movies.filter { it.category == item },
            viewModel,
            item
        )
    }

    override fun onFailedToRecycleView(holder: PagerViewHolder): Boolean = true

    override fun getItemCount(): Int = MoviesCategories.values().size

    private fun getItem(position: Int): MoviesCategories = MoviesCategories.values()[position]

}

class PagerViewHolder(
    private val binding: ViewpagerHolderBinding,
    private val viewModel: MoviesListViewModel,
    private val owner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var currMovies: List<MovieEntity>
    private lateinit var categoryOfPage: String

    init {
        binding.refreshContainer.apply {
            setProgressBackgroundColorSchemeResource(R.color.highlight_red_color)
            setOnRefreshListener {
                viewModel.reload()
            }
        }
    }

    fun bind(
        movies: List<MovieEntity>,
        viewModel: MoviesListViewModel,
        category: String
    ) {
        moviesAdapter = MoviesAdapter(movies, viewModel)
        binding.recyclerMovies.adapter = moviesAdapter
        currMovies = movies
        categoryOfPage = category
        viewModel.moviesList.observe(owner, this::updateList)
        viewModel.isLoading.observe(owner, this::stopLoading)
    }

    private fun updateList(newMoviesList: List<MovieEntity>) {
        val newMovies = newMoviesList.filter { it.category == categoryOfPage }
        if (viewModel.currentCategory == categoryOfPage || (newMovies.isNotEmpty() && currMovies.isEmpty())) {
            moviesAdapter.bindMovies(newMovies)
            val diffCallback = MoviesDiffUtilCallback(currMovies, newMovies)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(moviesAdapter)
            currMovies = newMovies
        }
    }

    private fun stopLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding.refreshContainer.isRefreshing = false
        }
    }
}