package dev.baharudin.themoviedb.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String
)
