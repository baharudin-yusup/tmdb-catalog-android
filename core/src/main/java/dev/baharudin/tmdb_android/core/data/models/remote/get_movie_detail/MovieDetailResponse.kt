package dev.baharudin.tmdb_android.core.data.models.remote.get_movie_detail

import com.google.gson.annotations.SerializedName
import dev.baharudin.tmdb_android.ProductionCompanies
import dev.baharudin.tmdb_android.ProductionCountries
import dev.baharudin.tmdb_android.SpokenLanguages
import dev.baharudin.tmdb_android.core.data.models.remote.get_genre_list.GenreResponse


data class MovieDetailResponse(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String,
    @SerializedName("belongs_to_collection") var belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerializedName("budget") var budget: Int? = null,
    @SerializedName("genres") var genres: ArrayList<GenreResponse>,
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Int,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("revenue") var revenue: Int? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("spoken_languages") var spokenLanguages: ArrayList<SpokenLanguages> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
)