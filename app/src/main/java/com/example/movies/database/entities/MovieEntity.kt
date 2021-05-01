package com.example.movies.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.database.DbContract

@Entity(tableName = DbContract.Movies.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_ID)
    val id: Long? = null,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_IS_ADULT)
    val isAdult: Boolean,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_GENRES)
    val genres: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RELEASE_DATE)
    val releaseDate: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_REVIEW_COUNT)
    val reviewCount: Int,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RATING)
    val rating: Int,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String?,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_DETAIL_IMAGE_URL)
    val detailImageUrl: String?,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_STORYLINE)
    val storyline: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_IS_LIKED)
    var isLiked: Boolean = false,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_CATEGORY)
    val category: String
)
