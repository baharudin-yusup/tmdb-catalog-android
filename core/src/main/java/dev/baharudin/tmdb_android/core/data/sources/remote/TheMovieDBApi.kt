package dev.baharudin.tmdb_android.core.data.sources.remote

import dev.baharudin.tmdb_android.core.data.models.remote.get_genre_list.GetGenreListResponse
import dev.baharudin.tmdb_android.core.data.models.remote.get_movie_detail.MovieDetailResponse
import dev.baharudin.tmdb_android.core.data.models.remote.get_movie_list.GetMovieListResponse
import dev.baharudin.tmdb_android.core.data.models.remote.get_review_list.GetReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApi {
    @GET("3/genre/movie/list")
    suspend fun getGenreList(): GetGenreListResponse

    @GET("3/discover/movie")
    suspend fun getMovieList(
        @Query("with_genres", encoded = true) withGenres: String,
        @Query("page") page: Int = 1,
        @Query("language") location: String = "en-US",
    ): GetMovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id", encoded = true) movieId: Int,
    ): MovieDetailResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id", encoded = true) movieId: Int,
        @Query("page") page: Int = 1,
    ): GetReviewListResponse
}