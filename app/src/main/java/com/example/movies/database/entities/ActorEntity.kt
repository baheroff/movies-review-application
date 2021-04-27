package com.example.movies.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.movies.database.DbContract

@Entity(tableName = DbContract.Actors.TABLE_NAME,
        foreignKeys = [ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(DbContract.Movies.COLUMN_NAME_ID),
            childColumns = arrayOf(DbContract.Actors.COLUMN_NAME_MOVIE_ID),
            onDelete = CASCADE
        )]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_ID)
    val id: Long? = null,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_NAME)
    val name: String,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String?,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)
