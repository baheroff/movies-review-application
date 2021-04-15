package com.example.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

class NetworkModule {

    private val _apiKey = "5eac8e9992fc517b9bd4ad319cd6b3a9"

    val apiKey: String
        get() = _apiKey

    private val baseUrl = "https://api.themoviedb.org/3/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient().newBuilder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @ExperimentalSerializationApi
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    @ExperimentalSerializationApi
    val moviesApi: TheMovieApiService = retrofitBuilder.create()
}

