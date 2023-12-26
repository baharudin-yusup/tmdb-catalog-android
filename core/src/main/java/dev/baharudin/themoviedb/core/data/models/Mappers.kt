package dev.baharudin.themoviedb.core.data.models

import dev.baharudin.themoviedb.core.data.models.remote.get_genre_list.GenreResponse
import dev.baharudin.themoviedb.core.data.models.remote.get_movie_list.MovieResponse
import dev.baharudin.themoviedb.core.data.models.remote.get_review_list.AuthorDetailResponse
import dev.baharudin.themoviedb.core.data.models.remote.get_review_list.ReviewResponse
import dev.baharudin.themoviedb.core.domain.entities.Author
import dev.baharudin.themoviedb.core.domain.entities.Genre
import dev.baharudin.themoviedb.core.domain.entities.Movie
import dev.baharudin.themoviedb.core.domain.entities.Review
import dev.baharudin.themoviedb.core.data.models.local.Genre as DbGenre
import dev.baharudin.themoviedb.core.data.models.local.Movie as DbMovie

fun GenreResponse.toDbEntity(): DbGenre = DbGenre(id, name)
fun GenreResponse.toEntity(): Genre = Genre(id, name)
fun DbGenre.toEntity(): Genre = Genre(id, name)

fun AuthorDetailResponse.toEntity(): Author = Author(name, username, avatarPath)

fun ReviewResponse.toEntity() = Review(
    author = authorDetails.toEntity(),
    content = content,
    createdAt = createdAt,
    id = id,
    updatedAt = updatedAt,
    url = url,
    rating = authorDetails.rating
)

fun MovieResponse.toEntity(genres: List<Genre>, isFavorite: Boolean) = Movie(
    backdropPath = backdropPath,
    genres = genres,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite
)

fun MovieResponse.toDbEntity(isFavorite: Boolean = false): DbMovie = DbMovie(
    id = id,
    genreIds = genreIds,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    isFavorite = isFavorite,
    overview = overview
)

fun Movie.toDbEntity(): DbMovie = DbMovie(
    id = id,
    genreIds = ArrayList(genres.map { it.id }),
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    isFavorite = isFavorite,
    overview = overview,
)

fun DbMovie.toEntity(genres: List<Genre> = listOf()): Movie = Movie(
    backdropPath = backdropPath,
    genres = genres.filter { genre -> genreIds.contains(genre.id) },
    id = id,
    originalLanguage = null,
    originalTitle = null,
    overview = overview,
    popularity = null,
    posterPath = posterPath,
    releaseDate = null,
    title = title,
    video = null,
    voteAverage = null,
    voteCount = null,
    isFavorite = isFavorite
)