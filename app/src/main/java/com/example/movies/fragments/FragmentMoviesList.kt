package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.Movie
import com.example.movies.adaptors.MoviesAdapter
import com.example.movies.R
import com.example.movies.viewmodels.MoviesListViewModel
import com.example.movies.viewmodels.ViewModelFactory

class FragmentMoviesList() : Fragment() {

    private val viewModel: MoviesListViewModel
        by activityViewModels { ViewModelFactory(requireContext()) }

    private var recyclerMovies: RecyclerView? = null

    private var onItemClickListener: OnItemClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

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
            onItemClickListener?.onItemClicked()
            viewModel.onItemClickedComplete()
        }
    }

    private fun setUpMoviesListAdapter(movies: List<Movie>) {
        recyclerMovies?.adapter = context?.let {
            MoviesAdapter(it, movies, viewModel)
        }
    }

    interface OnItemClickListener{
        fun onItemClicked()
    }
}