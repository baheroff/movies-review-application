package com.example.movies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.MoviesApp
import com.example.movies.models.MoviesListModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private vararg val movieId: Long?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> {
            MoviesListViewModel(MoviesApp.getRepository(), MoviesListModel())
        }
        MoviesDetailsViewModel::class.java -> {
            MoviesDetailsViewModel(MoviesApp.getRepository(), movieId.first())
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
