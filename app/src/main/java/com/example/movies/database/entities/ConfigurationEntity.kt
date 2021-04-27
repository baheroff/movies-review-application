package com.example.movies.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.database.DbContract

@Entity(tableName = DbContract.Configuration.TABLE_NAME)
data class ConfigurationEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.Configuration.COLUMN_NAME_SECURE_IMAGE_URL)
    val baseImageUrl: String
)