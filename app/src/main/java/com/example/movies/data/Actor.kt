package com.example.movies.data

import java.io.Serializable

data class Actor(
        val id: Int,
        val name: String,
        val imageUrl: String,
) : Serializable