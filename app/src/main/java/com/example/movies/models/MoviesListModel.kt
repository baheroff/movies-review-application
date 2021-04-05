package com.example.movies.models

import com.example.movies.R
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListModel {

    private val moviesApiService = NetworkModule()

    suspend fun loadMovies(chipId: Int): List<Movie> = withContext(Dispatchers.IO) {

        when (chipId) {
            R.id.now_play_categ_chip -> moviesApiService.moviesApi
                                            .getNowPlayingMovies(moviesApiService.apiKey).moviesList
            R.id.upcoming_categ_chip -> moviesApiService.moviesApi
                                            .getUpcomingMovies(moviesApiService.apiKey).moviesList
            R.id.top_rated_categ_chip -> moviesApiService.moviesApi
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

}