package dev.baharudin.tmdb_android.core.data.models.remote.get_movie_detail

import com.google.gson.annotations.SerializedName

data class SpokenLanguages(
    @SerializedName("english_name") var englishName: String? = null,
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("name") var name: String? = null
)