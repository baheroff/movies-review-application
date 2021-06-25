package com.example.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.database.entities.ActorEntity
import com.example.movies.database.entities.MovieEntity
import com.example.movies.database.MoviesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(
    private val repository: MoviesRepository,
    movieId: Long?
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TAG", "Db problem: ", throwable)
    }

    private val _actorsList = MutableLiveData<List<ActorEntity>>()
    private val _eventBackPressed = MutableLiveData<Boolean>()
    private val _movie = MutableLiveData<MovieEntity>()
    lateinit var baseImageUrl: String

    val actorsList: LiveData<List<ActorEntity>>
        get() = _actorsList

    val eventBackPressed: LiveData<Boolean>
        get() = _eventBackPressed

    val movie: LiveData<MovieEntity>
        get() = _movie

    init {
        getMovieAndActorsFromBd(movieId)
    }

    fun onBackPressed() {
        _eventBackPressed.value = true
    }

    fun onBackPressedComplete() {
        _eventBackPressed.value = false
    }

    private fun getMovieAndActorsFromBd(movieId: Long?) {
        viewModelScope.launch(exceptionHandler) {
            baseImageUrl = repository.getBaseImageUrl()

            _movie.value = repository.getMovieById(movieId)
            _actorsList.setValue(repository.getAllActorsByMovieId(movieId))
        }
    }
}