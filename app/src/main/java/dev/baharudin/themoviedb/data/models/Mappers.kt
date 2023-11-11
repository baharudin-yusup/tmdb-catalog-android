package dev.baharudin.themoviedb.data.models

import dev.baharudin.themoviedb.data.models.local.Genre as DbGenre
import dev.baharudin.themoviedb.data.models.remote.get_genre_list.GenreResponse
import dev.baharudin.themoviedb.data.models.remote.get_review_list.AuthorDetailResponse
import dev.baharudin.themoviedb.data.models.remote.get_review_list.ReviewResponse
import dev.baharudin.themoviedb.domain.entities.Author
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Review

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