package dev.baharudin.themoviedb.data.sources.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.baharudin.themoviedb.data.models.local.Genre

@Database(entities = [Genre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}