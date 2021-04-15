package com.example.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
