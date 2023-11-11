package dev.baharudin.themoviedb.data.models.remote.get_review_list

import com.google.gson.annotations.SerializedName
import dev.baharudin.themoviedb.domain.entities.Review

data class ReviewResponse(
    @SerializedName("author") var author: String,
    @SerializedName("author_details") var authorDetails: AuthorDetailResponse? = AuthorDetailResponse(),
    @SerializedName("content") var content: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("id") var id: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("url") var url: String
) {
    fun toEntity() = Review(
        author = author,
        content = content,
        createdAt = createdAt,
        id = id,
        updatedAt = updatedAt,
        url = url,
    )
}