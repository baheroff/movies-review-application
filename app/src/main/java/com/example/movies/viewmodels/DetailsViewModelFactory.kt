package com.example.movies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.MoviesApp
import com.example.movies.data.Genre
import com.example.movies.data.Movie

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val movieId: Long?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> {
            MoviesDetailsViewModel(MoviesApp.getRepository(), movieId)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}