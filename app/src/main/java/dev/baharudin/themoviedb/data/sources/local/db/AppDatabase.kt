package dev.baharudin.themoviedb.data.sources.local.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.baharudin.themoviedb.data.models.IntArrayListConverter
import dev.baharudin.themoviedb.data.models.local.Genre
import dev.baharudin.themoviedb.data.models.local.Movie

@Database(
    entities = [Genre::class, Movie::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(IntArrayListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
}