package com.example.movies.models

import com.example.movies.MoviesCategories
import com.example.movies.Result
import com.example.movies.data.Actor
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListModel {

    private val moviesApiService = NetworkModule()

    suspend fun loadMovies(category: MoviesCategories): List<Movie> = withContext(Dispatchers.IO) {
        when (category) {
            MoviesCategories.NOW_PLAYING -> moviesApiService.moviesApi
                                            .getNowPlayingMovies(moviesApiService.apiKey).moviesList
            MoviesCategories.UPCOMING -> moviesApiService.moviesApi
                                            .getUpcomingMovies(moviesApiService.apiKey).moviesList
            MoviesCategories.TOP_RATED -> moviesApiService.moviesApi
                                            .getTopRatedMovies(moviesApiService.apiKey).moviesList
            else -> moviesApiService.moviesApi
                        .getPopularMovies(moviesApiService.apiKey).moviesList
        }

    }

    suspend fun loadBaseImageUrl(): String = withContext(Dispatchers.IO) {
        moviesApiService.moviesApi.getConfig(moviesApiService.apiKey).imageUrl.baseImageUrl
    }

    suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
        moviesApiService.moviesApi.getGenres(moviesApiService.apiKey).genres
    }

    suspend fun loadActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        moviesApiService.moviesApi.getActors(movieId, moviesApiService.apiKey).moviesCast
    }
}