package com.example.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUrl(
    @SerialName("images")
    val imageUrl: SecureImageUrl
)

@Serializable
data class SecureImageUrl(
    @SerialName("secure_base_url")
    val baseImageUrl: String
)
