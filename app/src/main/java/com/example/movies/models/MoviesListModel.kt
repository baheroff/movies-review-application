package com.example.movies.models

import android.content.Context
import com.example.movies.data.JsonMovieRepository
import com.example.movies.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListModel(
    private val context: Context
) {
    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val moviesRepo = JsonMovieRepository(context)
        moviesRepo.loadMovies()
    }
}