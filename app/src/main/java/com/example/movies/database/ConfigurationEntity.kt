package com.example.movies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbContract.Configuration.TABLE_NAME)
data class ConfigurationEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.Configuration.COLUMN_NAME_SECURE_IMAGE_URL)
    val baseImageUrl: String
)