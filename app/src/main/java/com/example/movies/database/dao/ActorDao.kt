package com.example.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.database.entities.ActorEntity

@Dao
interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(actors: List<ActorEntity>)

    @Query("SELECT * FROM actors WHERE movie_id = :movieId")
    suspend fun getAllByMovieId(movieId: Long?): List<ActorEntity>
}