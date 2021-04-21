package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movies.adaptors.MoviesAdapter
import com.example.movies.R
import com.example.movies.database.MovieEntity
import com.example.movies.viewmodels.MoviesListViewModel
import com.example.movies.viewmodels.ListViewModelFactory
import com.google.android.material.chip.ChipGroup

class FragmentMoviesList : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels{
        ListViewModelFactory()
    }

    private var refreshContainer: SwipeRefreshLayout? = null
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
        setUpListeners()

        viewModel.isLoading.observe(viewLifecycleOwner, this::stopLoading)
        viewModel.moviesList.observe(viewLifecycleOwner, this::setUpMoviesListAdapter)
        viewModel.eventItemClicked.observe(viewLifecycleOwner, this::openMovieDetails)
        viewModel.errorFound.observe(viewLifecycleOwner, this::showToast)
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
        refreshContainer = null
    }

    private fun initViews(view: View) {
        recyclerMovies = view.findViewById(R.id.moviesList)
        chipsGroup = view.findViewById(R.id.movies_categories)
        refreshContainer = view.findViewById(R.id.refreshContainer)
        refreshContainer?.setProgressBackgroundColorSchemeResource(R.color.highlight_red_color)
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

    private fun showToast(errorFound: Boolean) {
        if (errorFound) {
            viewModel.errorHandled()
            Toast.makeText(requireContext(), "Reload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopLoading(isLoading: Boolean) {
        if (!isLoading) {
            refreshContainer?.isRefreshing = false
        }
    }

    private fun setUpListeners() {
        chipsGroup?.setOnCheckedChangeListener { _, checkedId ->
            viewModel.chipChecked(checkedId)
        }
        refreshContainer?.setOnRefreshListener {
            viewModel.reload()
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(movieId: Long?)
    }
}