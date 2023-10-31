package dev.baharudin.themoviedb.data.models.get_review_list

import com.google.gson.annotations.SerializedName

data class AuthorDetailResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("avatar_path") var avatarPath: String? = null,
    @SerializedName("rating") var rating: String? = null
)