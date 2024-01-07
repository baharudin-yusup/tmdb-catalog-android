package dev.baharudin.tmdb_android.core.data.models.remote.get_genre_list

import com.google.gson.annotations.SerializedName


data class GenreResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
)