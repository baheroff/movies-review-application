package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.Movie
import com.example.movies.adaptors.MoviesAdapter
import com.example.movies.R
import com.example.movies.viewmodels.MoviesListViewModel

class FragmentMoviesList(context: Context) : Fragment() {

    private val viewModel: MoviesListViewModel = MoviesListViewModel(context = context)

    private var recyclerMovies: RecyclerView? = null

    private var onItemClickListener: OnItemClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        viewModel.loadMoviesFromRepository()

        viewModel.moviesList.observe(this.viewLifecycleOwner, this::setUpMoviesListAdapter)
        viewModel.eventItemClicked.observe(this.viewLifecycleOwner, this::openMovieDetails)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnItemClickListener){
            onItemClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemClickListener = null
        recyclerMovies?.adapter = null
        recyclerMovies = null
    }

    private fun initViews(view: View) {
        recyclerMovies = view.findViewById(R.id.moviesList)
    }

    private fun openMovieDetails(isClicked: Boolean) {
        if (isClicked) {
            onItemClickListener?.onItemClicked(viewModel.movieDetailsToOpen)
            viewModel.onItemClickedComplete()
        }
    }

    private fun setUpMoviesListAdapter(movies: List<Movie>) {
        recyclerMovies?.adapter = context?.let {
            MoviesAdapter(it, movies, viewModel)
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(movie: Movie)
    }
}