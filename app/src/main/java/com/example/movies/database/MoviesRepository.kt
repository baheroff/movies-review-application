package com.example.movies.database

import com.example.movies.MoviesCategories
import com.example.movies.data.Actor
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.database.entities.ActorEntity
import com.example.movies.database.entities.ConfigurationEntity
import com.example.movies.database.entities.MovieEntity
import com.example.movies.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val moviesDb: MoviesDatabase,
    private val moviesApiService: NetworkModule
) {
    suspend fun insertImageUrl(
        imageUrl: String
    ) = withContext(Dispatchers.IO) {
        moviesDb.configurationDao().insertImageUrl(toConfigEntity(imageUrl))
    }

    suspend fun getBaseImageUrl(): String = withContext(Dispatchers.IO) {
        moviesDb.configurationDao().getBaseImageUrl()
    }

    suspend fun insertAllActors(
        actors: List<Actor>,
        movieId: Long
    ) = withContext(Dispatchers.IO) {
        moviesDb.actorDao().insertAll(actors.map { toActorEntity(it, movieId) })
    }

    suspend fun getAllActorsByMovieId(
        movieId: Long?
    ): List<ActorEntity> = withContext(Dispatchers.IO) {
        moviesDb.actorDao().getAllByMovieId(movieId)
    }

    suspend fun getMovieById(
        movieId: Long?
    ): MovieEntity = withContext(Dispatchers.IO) {
        moviesDb.movieDao().getMovieById(movieId)
    }

    suspend fun updateAllMoviesByCategory(
        movies: List<Movie>,
        genres: List<Genre>,
        category: String
    ): List<Long> = withContext(Dispatchers.IO) {
        moviesDb.movieDao()
            .updateMovies(category, movies.map { toMovieEntity(it, genres, category) })
    }

    suspend fun getAllMovies(): List<MovieEntity> = withContext(Dispatchers.IO) {
        moviesDb.movieDao().getAll()
    }

    private fun toMovieEntity(
        movie: Movie,
        genres: List<Genre>,
        category: String
    ): MovieEntity = MovieEntity(
        isAdult = movie.isAdult,
        title = movie.title,
        genres = movie.genresIds.joinToString { id ->
            genres.find {
                it.id == id
            }?.name.toString()
        },
        releaseDate = movie.releaseDate,
        reviewCount = movie.reviewCount,
        rating = (movie.rating / 2).toInt(),
        imageUrl = movie.imageUrl,
        detailImageUrl = movie.detailImageUrl,
        storyline = movie.storyLine,
        isLiked = false,
        category = category
    )

    private fun toActorEntity(
        actor: Actor,
        movieId: Long
    ): ActorEntity = ActorEntity(
        name = actor.name,
        imageUrl = actor.imageUrl,
        movieId = movieId
    )

    private fun toConfigEntity(
        imageUrl: String
    ): ConfigurationEntity = ConfigurationEntity(
        baseImageUrl = imageUrl
    )

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