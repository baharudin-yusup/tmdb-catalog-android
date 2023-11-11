package dev.baharudin.themoviedb.data.models.remote.get_genre_list

import com.google.gson.annotations.SerializedName
import dev.baharudin.themoviedb.domain.entities.Genre


data class GenreResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
) {
    fun toEntity() = Genre(id, name)
}