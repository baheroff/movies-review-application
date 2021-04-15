package com.example.movies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>): List<Long>

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getAllByCategory(category: String): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Long?): MovieEntity

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun deleteAll(category: String)
}