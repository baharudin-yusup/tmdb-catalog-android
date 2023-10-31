package dev.baharudin.themoviedb.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val genreIds: ArrayList<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
): Parcelable