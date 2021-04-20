package com.example.movies.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.MoviesCategories
import com.example.movies.R
import com.example.movies.data.Genre
import com.example.movies.database.MovieEntity
import com.example.movies.database.MoviesRepository
import com.example.movies.models.MoviesListModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val repository: MoviesRepository,
    private val moviesListModel: MoviesListModel
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TAG", "Network/db problem: ", throwable)
        _errorFound.value = true
    }

    private var currentCategory = MutableStateFlow(MoviesCategories.NOW_PLAYING)

    private lateinit var _genres: List<Genre>
    private lateinit var _baseImageUrl: String
    private var _movieDetailsToOpen: Long? = null

    private val _errorFound = MutableLiveData<Boolean>()
    private val _eventItemClicked = MutableLiveData<Boolean>()
    private val _moviesList: LiveData<List<MovieEntity>> = currentCategory
        .flatMapLatest { category ->
            repository.getAllMoviesByCategoryFlow(category.toString())
        }.asLiveData()

    val genres: List<Genre>
        get() = _genres

    val baseImageUrl: String
        get() = _baseImageUrl

    val movieDetailsToOpen: Long?
        get() = _movieDetailsToOpen

    val eventItemClicked: LiveData<Boolean>
        get() = _eventItemClicked

    val errorFound: LiveData<Boolean>
        get() = _errorFound

    val moviesList: LiveData<List<MovieEntity>>
        get() = _moviesList

    init {
        loadGenres()
        loadBaseImageUrl()
    }

    fun reload() {
        loadMoviesWithActorsByCategory(currentCategory.value)
    }

    fun errorHandled() {
        _errorFound.value = false
    }

    fun onItemClicked(movieId: Long?) {
        _movieDetailsToOpen = movieId
        _eventItemClicked.value = true
    }

    fun onItemClickedComplete() {
        _eventItemClicked.value = false
    }

    fun chipChecked(checkedId: Int) {
        currentCategory.value = when (checkedId) {
            R.id.popular_categ_chip -> MoviesCategories.POPULAR
            R.id.top_rated_categ_chip -> MoviesCategories.TOP_RATED
            R.id.upcoming_categ_chip -> MoviesCategories.UPCOMING
            else -> MoviesCategories.NOW_PLAYING
        }
    }

    private fun loadMoviesWithActorsByCategory(category: MoviesCategories) {
        viewModelScope.launch(exceptionHandler) {
            val moviesRemote = moviesListModel.loadMovies(category)
            val moviesIds = repository.updateAllMoviesByCategory(moviesRemote, _genres, category.toString())

            for ((i, movie) in moviesRemote.withIndex()) {
                repository.insertAllActors(moviesListModel.loadActors(movie.id), moviesIds[i])
            }
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