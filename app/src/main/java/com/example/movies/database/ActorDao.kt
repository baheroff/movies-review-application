package com.example.movies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(actors: List<ActorEntity>)

    @Query("SELECT * FROM actors WHERE movie_id = :movieId")
    suspend fun getAllByMovieId(movieId: Long?): List<ActorEntity>

    @Query("DELETE FROM actors")
    suspend fun deleteAll()
}