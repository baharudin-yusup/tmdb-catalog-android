package dev.baharudin.themoviedb.data.models

import dev.baharudin.themoviedb.data.models.remote.get_genre_list.GenreResponse
import dev.baharudin.themoviedb.data.models.remote.get_movie_list.MovieResponse
import dev.baharudin.themoviedb.data.models.remote.get_review_list.AuthorDetailResponse
import dev.baharudin.themoviedb.data.models.remote.get_review_list.ReviewResponse
import dev.baharudin.themoviedb.domain.entities.Author
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.entities.Review
import dev.baharudin.themoviedb.data.models.local.Genre as DbGenre
import dev.baharudin.themoviedb.data.models.local.Movie as DbMovie

fun GenreResponse.toDbEntity(): DbGenre = DbGenre(id, name)
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
    isFavorite = isFavorite
)

fun Movie.toDbEntity(): DbMovie = DbMovie(
    id = id,
    genreIds = if (genres != null) ArrayList(genres.map { it.id }) else ArrayList(),
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    isFavorite = isFavorite
)

fun DbMovie.toEntity(): Movie = Movie(
    backdropPath = backdropPath,
    genres = null,
    id = id,
    originalLanguage = null,
    originalTitle = null,
    overview = null,
    popularity = null,
    posterPath = posterPath,
    releaseDate = null,
    title = title,
    video = null,
    voteAverage = null,
    voteCount = null,
    isFavorite = isFavorite
)