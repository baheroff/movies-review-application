package com.example.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.R
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.models.MoviesListModel
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {

    private val moviesListModel: MoviesListModel = MoviesListModel()

    private lateinit var _genres: List<Genre>
    private lateinit var _baseImageUrl: String
    private lateinit var _movieDetailsToOpen: Movie

    private val _eventItemClicked = MutableLiveData<Boolean>()
    private val _moviesList = MutableLiveData<List<Movie>>()

    val genres: List<Genre>
        get() = _genres

    val baseImageUrl: String
        get() = _baseImageUrl

    val movieDetailsToOpen: Movie
        get() = _movieDetailsToOpen

    val eventItemClicked: LiveData<Boolean>
        get() = _eventItemClicked

    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    init {
        loadGenres()
        loadBaseImageUrl()
        loadMovies(R.id.now_play_categ_chip)
    }

    fun onItemClicked(movie: Movie) {
        _movieDetailsToOpen = movie
        _eventItemClicked.value = true
    }

    fun onItemClickedComplete() {
        _eventItemClicked.value = false
    }

    fun chipChecked(chipId: Int) {
        loadMovies(chipId)
    }

    private fun loadMovies(chipId: Int) {
        viewModelScope.launch {
            _moviesList.value = moviesListModel.loadMovies(chipId)
        }
    }

    private fun loadBaseImageUrl() {
        viewModelScope.launch {
            _baseImageUrl = moviesListModel.loadBaseImageUrl()
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _genres = moviesListModel.loadGenres()
        }
    }
}