package com.example.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.data.Genre
import com.example.movies.database.MovieEntity
import com.example.movies.database.MoviesRepository
import com.example.movies.models.MoviesListModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val repository: MoviesRepository,
    private val moviesListModel: MoviesListModel
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TAG", "Network/db problem: ", throwable)
    }

    private var currentCategory: MoviesCategories = MoviesCategories.NOW_PLAYING

    private lateinit var _genres: List<Genre>
    private lateinit var _baseImageUrl: String
    private var _movieDetailsToOpen: Long? = null

    private val _eventItemClicked = MutableLiveData<Boolean>()
    private val _moviesList = MutableLiveData<List<MovieEntity>>()

    val genres: List<Genre>
        get() = _genres

    val baseImageUrl: String
        get() = _baseImageUrl

    val movieDetailsToOpen: Long?
        get() = _movieDetailsToOpen

    val eventItemClicked: LiveData<Boolean>
        get() = _eventItemClicked

    val moviesList: LiveData<List<MovieEntity>>
        get() = _moviesList

    init {
        loadGenres()
        loadBaseImageUrl()
        loadMovies(MoviesCategories.NOW_PLAYING)
        loadMoviesAndActorsFromNetwork()
    }

    fun onItemClicked(movieId: Long?) {
        _movieDetailsToOpen = movieId
        _eventItemClicked.value = true
    }

    fun onItemClickedComplete() {
        _eventItemClicked.value = false
    }

    fun chipChecked(checkedId: Int) {
        currentCategory = when (checkedId) {
            R.id.popular_categ_chip -> MoviesCategories.POPULAR
            R.id.top_rated_categ_chip -> MoviesCategories.TOP_RATED
            R.id.upcoming_categ_chip -> MoviesCategories.UPCOMING
            else -> MoviesCategories.NOW_PLAYING
        }
        loadMovies(currentCategory)
    }

    private fun loadMovies(category: MoviesCategories) {
        viewModelScope.launch(exceptionHandler) {
            val moviesLocal = repository.getAllMoviesByCategory(category.toString())

            if (moviesLocal.isNotEmpty()) {
                _moviesList.value = moviesLocal
            }
        }
    }

    private fun loadMoviesAndActorsFromNetwork() {
        viewModelScope.launch(exceptionHandler) {
            for (category in MoviesCategories.values()) {
                val moviesRemote = moviesListModel.loadMovies(category)

                repository.deleteAllMoviesByCategory(category.toString())
                val moviesIds = repository.insertAllMovies(moviesRemote, _genres, category.toString())

                for ((i, movie) in moviesRemote.withIndex()) {
                    repository.insertAllActors(moviesListModel.loadActors(movie.id), moviesIds[i])
                }
            }
            _moviesList.setValue(repository.getAllMoviesByCategory(currentCategory.toString()))
        }
    }

    private fun loadBaseImageUrl() {
        viewModelScope.launch(exceptionHandler) {
            val imageUrl = repository.getBaseImageUrl()

            if (!imageUrl.isNullOrEmpty()) {
                _baseImageUrl = imageUrl
            }

            val imageUrlRemote = moviesListModel.loadBaseImageUrl()
            repository.insertImageUrl(imageUrlRemote)
            _baseImageUrl = imageUrlRemote
        }
    }

    private fun loadGenres() {
        viewModelScope.launch(exceptionHandler) {
            _genres = moviesListModel.loadGenres()
        }
    }
}