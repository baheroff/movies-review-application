package com.example.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
        @SerialName("cast")
        val moviesCast: List<Actor>
)

@Serializable
data class Actor(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val imageUrl: String?,
)