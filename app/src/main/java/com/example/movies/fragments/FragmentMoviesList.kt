package com.example.movies.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MoviesCategories
import com.example.movies.adaptors.MoviesAdapter
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.viewmodels.MoviesListViewModel
import com.example.movies.viewmodels.ListViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.delay

class FragmentMoviesList : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels{
        ListViewModelFactory()
    }

    private var recyclerMovies: RecyclerView? = null
    private var chipsGroup: ChipGroup? = null

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpChipsListener()

        viewModel.moviesList.observe(viewLifecycleOwner, this::setUpMoviesListAdapter)
        viewModel.eventItemClicked.observe(viewLifecycleOwner, this::openMovieDetails)
    }

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
        chipsGroup = null
    }

    private fun initViews(view: View) {
        recyclerMovies = view.findViewById(R.id.moviesList)
        chipsGroup = view.findViewById(R.id.movies_categories)
    }

    private fun openMovieDetails(isClicked: Boolean) {
        if (isClicked) {
            onItemClickListener?.onItemClicked(viewModel.movieDetailsToOpen)
            viewModel.onItemClickedComplete()
        }
    }

    private fun setUpMoviesListAdapter(movies: List<MovieEntity>) {
        recyclerMovies?.adapter = context?.let {
            MoviesAdapter(it, movies, viewModel)
        }
    }

    private fun setUpChipsListener() {
        chipsGroup?.setOnCheckedChangeListener { _, checkedId ->
            viewModel.chipChecked(checkedId)
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(movieId: Long?)
    }

    companion object {
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }
}