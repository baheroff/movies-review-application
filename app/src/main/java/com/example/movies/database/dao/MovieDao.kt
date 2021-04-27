package com.example.movies.database.dao

import androidx.room.*
import com.example.movies.database.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>): List<Long>

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getAllByCategory(category: String): List<MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllFlow(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Long?): MovieEntity

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun deleteAll(category: String)

    @Transaction
    suspend fun updateMovies(
        category: String,
        movies: List<MovieEntity>
    ): List<Long> {
        deleteAll(category)
        return insertAll(movies)
    }
}