package com.example.movies

import android.app.Application
import com.example.movies.database.MoviesDatabase
import com.example.movies.database.MoviesRepository
import com.example.movies.network.NetworkModule

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = MoviesDatabase.getDatabase(applicationContext)
        repository = MoviesRepository(database, NetworkModule())
    }

    companion object {
        private lateinit var repository: MoviesRepository
        fun getRepository(): MoviesRepository = repository
    }
}