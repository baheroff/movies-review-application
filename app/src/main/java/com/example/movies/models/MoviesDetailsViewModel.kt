package com.example.movies.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MoviesDetailsViewModel {

    private val _eventBackPressed = MutableLiveData<Boolean>()

    val eventBackPressed: LiveData<Boolean>
        get() = _eventBackPressed

    fun onBackPressed() {
        _eventBackPressed.value = true
    }

    fun onBackPressedComplete() {
        _eventBackPressed.value = false
    }
}