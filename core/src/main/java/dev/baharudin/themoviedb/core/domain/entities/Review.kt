package dev.baharudin.themoviedb.core.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val author: Author,
    var content: String,
    var createdAt: String,
    var id: String,
    var updatedAt: String,
    var url: String,
    val rating: String?
): Parcelable