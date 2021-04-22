package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.viewmodels.MoviesListViewModel

class ViewPagerAdapter(
    private var movies: List<MovieEntity>,
    private val viewModel: MoviesListViewModel
) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context)
                                    .inflate(R.layout.viewpager_holder, parent, false),
                               movies,
                               viewModel)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = getItem(position).toString()
        holder.bind(movies.filter { it.category == item }, viewModel)
    }

    override fun getItemCount(): Int = MoviesCategories.values().size

    private fun getItem(position: Int): MoviesCategories = MoviesCategories.values()[position]
}

class PagerViewHolder(
    view: View,
    movies: List<MovieEntity>,
    viewModel: MoviesListViewModel
) : RecyclerView.ViewHolder(view) {

    private val recyclerView: RecyclerView = itemView.findViewById(R.id.moviesList)
    private val refreshLayout: SwipeRefreshLayout = itemView.findViewById(R.id.refreshContainer)
   // private val adapter = MoviesAdapter(movies, viewModel)

    init {
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.highlight_red_color)
    }

    fun bind(
        movies: List<MovieEntity>,
        viewModel: MoviesListViewModel
    ) {
        recyclerView.adapter = MoviesAdapter(movies, viewModel)
        //adapter.movies = movies
        //adapter.notifyDataSetChanged()
        refreshLayout.setOnRefreshListener {
            viewModel.reload()
        }
    }
}