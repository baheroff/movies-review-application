package com.example.movies.database

object DbContract {

    const val DATABASE_NAME = "Movies.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_IS_ADULT = "is_adult"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_RELEASE_DATE = "release_date"
        const val COLUMN_NAME_REVIEW_COUNT = "review_count"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
        const val COLUMN_NAME_DETAIL_IMAGE_URL = "detail_image_url"
        const val COLUMN_NAME_STORYLINE = "storyline"
        const val COLUMN_NAME_IS_LIKED = "is_liked"
        const val COLUMN_NAME_CATEGORY = "category"
    }

    object Actors {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
        const val COLUMN_NAME_MOVIE_ID = "movie_id"
    }

    object Configuration {
        const val TABLE_NAME = "configurations"

        const val COLUMN_NAME_SECURE_IMAGE_URL = "base_image_url"
    }
}