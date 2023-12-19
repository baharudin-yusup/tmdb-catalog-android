package dev.baharudin.themoviedb.core.data.models.remote.get_review_list

import com.google.gson.annotations.SerializedName

data class AuthorDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("rating") val rating: String?
)