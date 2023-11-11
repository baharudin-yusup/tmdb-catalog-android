package dev.baharudin.themoviedb.data.models

import dev.baharudin.themoviedb.data.models.local.Genre as DbGenre
import dev.baharudin.themoviedb.data.models.remote.get_genre_list.GenreResponse
import dev.baharudin.themoviedb.domain.entities.Genre

fun GenreResponse.toDbEntity(): DbGenre = DbGenre(id, name)
fun DbGenre.toEntity(): Genre = Genre(id, name)