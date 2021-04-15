package com.example.movies

import android.app.Application
import com.example.movies.database.MoviesDatabase
import com.example.movies.database.MoviesRepository

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = MoviesDatabase.getDatabase(applicationContext)
        repository = MoviesRepository(database)
    }

    companion object {
        private lateinit var repository: MoviesRepository
        fun getRepository(): MoviesRepository = repository
    }
}