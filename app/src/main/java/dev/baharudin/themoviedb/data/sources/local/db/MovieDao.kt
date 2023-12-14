package dev.baharudin.themoviedb.data.sources.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.baharudin.themoviedb.data.models.local.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun loadAllFavoriteMovie(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun loadMovieById(movieId: Int): Movie

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun loadMovieByIds(movieIds: IntArray): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg movies: Movie)

    @Update
    fun updateMovie(movie: Movie): Int
}