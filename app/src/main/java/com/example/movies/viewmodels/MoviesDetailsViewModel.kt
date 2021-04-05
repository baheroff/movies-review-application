package com.example.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.Actor
import com.example.movies.models.MoviesDetailsModel
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(
    movieId: Int
): ViewModel() {

    private val moviesDetailsModel: MoviesDetailsModel = MoviesDetailsModel(movieId)

    private val _actorsList = MutableLiveData<List<Actor>>()
    private val _eventBackPressed = MutableLiveData<Boolean>()

    val actorsList: LiveData<List<Actor>>
        get() = _actorsList

    val eventBackPressed: LiveData<Boolean>
        get() = _eventBackPressed

    init {
        loadActors()
    }

    fun onBackPressed() {
        _eventBackPressed.value = true
    }

    fun onBackPressedComplete() {
        _eventBackPressed.value = false
    }

    private fun loadActors() {
        viewModelScope.launch {
            _actorsList.value = moviesDetailsModel.loadActors()
        }
    }
}