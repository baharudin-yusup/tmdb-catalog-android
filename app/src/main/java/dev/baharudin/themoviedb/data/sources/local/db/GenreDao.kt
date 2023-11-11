package dev.baharudin.themoviedb.data.sources.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.baharudin.themoviedb.data.models.local.Genre

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres WHERE id IN (:genreIds)")
    fun loadAllByIds(genreIds: IntArray): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg genres: Genre)
}