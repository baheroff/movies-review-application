package com.example.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

class NetworkModule {

    private val _apiKey = "5eac8e9992fc517b9bd4ad319cd6b3a9"

    val apiKey: String
        get() = _apiKey

    private val baseUrl = "https://api.themoviedb.org/3/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi: TheMovieApiService = retrofitBuilder.create()
}

