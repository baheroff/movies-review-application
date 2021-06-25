package com.example.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesCategories
import com.example.movies.data.Genre
import com.example.movies.database.entities.MovieEntity
import com.example.movies.database.MoviesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.Dispatchers

class MoviesListViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(javaClass.simpleName, "Network/db problem: ", throwable)
        _errorFound.value = true
    }

    private var _currentCategory = MoviesCategories.NOW_PLAYING

    private lateinit var genres: List<Genre>
    private lateinit var _baseImageUrl: String
    private var _movieDetailsToOpen: Long? = null

    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorFound = MutableLiveData<Boolean>()
    private val _eventItemClicked = MutableLiveData<Boolean>()

    private val _moviesList = MutableLiveData<List<MovieEntity>>()

    val baseImageUrl: String
        get() = _baseImageUrl

    val movieDetailsToOpen: Long?
        get() = _movieDetailsToOpen

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val eventItemClicked: LiveData<Boolean>
        get() = _eventItemClicked

    val errorFound: LiveData<Boolean>
        get() = _errorFound

    val moviesList: LiveData<List<MovieEntity>>
        get() = _moviesList

    val currentCategory: String
        get() = _currentCategory.toString()

    init {
        viewModelScope.launch(exceptionHandler) {
            supervisorScope {
                launch { loadBaseImageUrl() }
                launch { loadGenres() }
            }
            val movies = repository.getAllMovies()
            if (movies.isEmpty()) {
                _isLoading.value = true
                for (category in MoviesCategories.values()) {
                    loadMoviesWithActorsByCategory(category)
                }
                _moviesList.value = repository.getAllMovies()
                _isLoading.value = false
            } else {
                _moviesList.value = movies
            }
        }
    }

    fun reload() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            loadMoviesWithActorsByCategory(_currentCategory)
            _moviesList.value = repository.getAllMovies()
            _isLoading.value = false
        }
    }

    fun errorHandled() {
        _errorFound.value = false
        _isLoading.value = false
    }

    fun onItemClicked(movieId: Long?) {
        _movieDetailsToOpen = movieId
        _eventItemClicked.value = true
    }

    fun onItemClickedComplete() {
        _eventItemClicked.value = false
    }

    fun pageSelected(category: MoviesCategories) {
        _currentCategory = category
    }

    private suspend fun loadMoviesWithActorsByCategory(category: MoviesCategories) = withContext(
        Dispatchers.IO
    ) {
        val moviesRemote = repository.loadMovies(category)
        val moviesIds =
            repository.updateAllMoviesByCategory(moviesRemote, genres, category.toString())

        for ((i, movie) in moviesRemote.withIndex()) {
            repository.insertAllActors(repository.loadActors(movie.id), moviesIds[i])
        }
    }

    private suspend fun loadBaseImageUrl() = withContext(Dispatchers.IO) {

        val imageUrl = repository.getBaseImageUrl()

        if (!imageUrl.isNullOrEmpty()) {
            _baseImageUrl = imageUrl
        }

        val imageUrlRemote = repository.loadBaseImageUrl()

        repository.insertImageUrl(imageUrlRemote)
        _baseImageUrl = imageUrlRemote
    }

    private suspend fun loadGenres() = withContext(Dispatchers.IO) {

        val genresRemote = repository.loadGenres()

        if (!genresRemote.isNullOrEmpty()) {
            genres = genresRemote
        }
    }
}