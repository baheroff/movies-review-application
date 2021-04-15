package com.example.movies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageUrl(imageUrl: ConfigurationEntity)

    @Query("SELECT * FROM configurations LIMIT 1")
    suspend fun getBaseImageUrl(): String
}