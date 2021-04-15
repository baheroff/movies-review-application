package com.example.movies.database

import com.example.movies.data.Actor
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val moviesDb: MoviesDatabase
) {

    suspend fun deleteActors() = withContext(Dispatchers.IO) {
        moviesDb.actorDao().deleteAll()
    }

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

    suspend fun deleteAllMoviesByCategory(
        category: String
    ) = withContext(Dispatchers.IO) {
        moviesDb.movieDao().deleteAll(category)
    }

    suspend fun getMovieById(
        movieId: Long?
    ): MovieEntity = withContext(Dispatchers.IO) {
        moviesDb.movieDao().getMovieById(movieId)
    }

    suspend fun insertAllMovies(
        movies: List<Movie>,
        genres: List<Genre>,
        category: String
    ) = withContext(Dispatchers.IO) {
        moviesDb.movieDao().insertAll(movies.map { toMovieEntity(it, genres, category) })
    }

    suspend fun getAllMoviesByCategory(
        category: String
    ): List<MovieEntity> = withContext(Dispatchers.IO) {
        moviesDb.movieDao().getAllByCategory(category)
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
        rating = movie.rating,
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
}