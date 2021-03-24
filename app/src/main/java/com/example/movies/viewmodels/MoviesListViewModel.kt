package com.example.movies.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Movie
import com.example.movies.models.MoviesListModel
import kotlinx.coroutines.launch

class MoviesListViewModel(
    context: Context
) : ViewModel() {

    private val moviesListModel: MoviesListModel = MoviesListModel(context)

    private lateinit var _movieDetailsToOpen: Movie
    private val _eventItemClicked = MutableLiveData<Boolean>()
    private val _moviesList = MutableLiveData<List<Movie>>()

    val movieDetailsToOpen: Movie
        get() = _movieDetailsToOpen

    val eventItemClicked: LiveData<Boolean>
        get() = _eventItemClicked

    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    fun onItemClicked(movie: Movie) {
        _movieDetailsToOpen = movie
        _eventItemClicked.value = true
    }

    fun onItemClickedComplete() {
        _eventItemClicked.value = false
    }

    fun loadMoviesFromRepository() {
        viewModelScope.launch {
            _moviesList.value = moviesListModel.loadMovies()
        }
    }
}