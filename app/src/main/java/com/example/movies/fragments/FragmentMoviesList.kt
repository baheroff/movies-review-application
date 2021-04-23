package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.adapters.ViewPagerAdapter
import com.example.movies.database.MovieEntity
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewmodels.MoviesListViewModel
import com.example.movies.viewmodels.ListViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class FragmentMoviesList : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding

    private val viewModel: MoviesListViewModel by viewModels{
        ListViewModelFactory()
    }

    private lateinit var tabMediator: TabLayoutMediator

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initTabMediator()
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
    }

    private fun openMovieDetails(isClicked: Boolean) {
        if (isClicked) {
            onItemClickListener?.onItemClicked(viewModel.movieDetailsToOpen)
            viewModel.onItemClickedComplete()
        }
    }

    private fun initTabMediator() {
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
            tab.text = MoviesCategories.values()[position].toString().replace("_", " ")
        }
    }

    private fun setUpMoviesListAdapter(movies: List<MovieEntity>) {
        if (!tabMediator.isAttached) {
            binding.pager.adapter = ViewPagerAdapter(movies, viewModel)
            tabMediator.attach()
        }
    }

    private fun showToast(errorFound: Boolean) {
        if (errorFound) {
            viewModel.errorHandled()
            Toast.makeText(requireContext(), "Load failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding.pager.findViewById<SwipeRefreshLayout>(R.id.refreshContainer)?.apply {
                isRefreshing = false
            }
        }
    }

    private fun setUpListeners() {
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.pageSelected(MoviesCategories.values()[position])
            }
        })
    }

    interface OnItemClickListener{
        fun onItemClicked(movieId: Long?)
    }
}