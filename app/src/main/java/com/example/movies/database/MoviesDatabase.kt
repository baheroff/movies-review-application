package com.example.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.database.dao.ActorDao
import com.example.movies.database.dao.ConfigurationDao
import com.example.movies.database.dao.MovieDao
import com.example.movies.database.entities.ActorEntity
import com.example.movies.database.entities.ConfigurationEntity
import com.example.movies.database.entities.MovieEntity

@Database(entities = [MovieEntity::class,
                      ActorEntity::class,
                      ConfigurationEntity::class],
          version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun configurationDao(): ConfigurationDao

    companion object {

        private lateinit var INSTANCE: MoviesDatabase

        fun getDatabase(appContext: Context): MoviesDatabase {
            synchronized(MoviesDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        appContext,
                        MoviesDatabase::class.java,
                        DbContract.DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
