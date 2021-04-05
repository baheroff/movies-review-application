package com.example.movies.models

import com.example.movies.data.Actor
import com.example.movies.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDetailsModel(
    private val movieId: Int
) {
    private val moviesApiService = NetworkModule()

    suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        moviesApiService.moviesApi.getActors(movieId, moviesApiService.apiKey).moviesCast
    }
}