package com.example.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesList(
        @SerialName("results")
        val moviesList: List<Movie>
)

@Serializable
data class Movie(
        @SerialName("id")
        val id: Int,
        @SerialName("adult")
        val pgAge: Boolean,
        @SerialName("title")
        val title: String,
        @SerialName("genre_ids")
        val genresIds: List<Int>,
        @SerialName("release_date")
        val releaseDate: String,
        @SerialName("vote_count")
        val reviewCount: Int,
        @SerialName("vote_average")
        val rating: Float,
        @SerialName("poster_path")
        val imageUrl: String?,
        @SerialName("backdrop_path")
        val detailImageUrl: String?,
        @SerialName("overview")
        val storyLine: String,
)


