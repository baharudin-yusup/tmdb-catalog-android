package dev.baharudin.tmdb_android.core.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.baharudin.tmdb_android.core.data.models.remote.get_review_list.ReviewResponse
import dev.baharudin.tmdb_android.core.data.sources.remote.TheMovieDBApi
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import retrofit2.HttpException
import java.io.IOException

class ReviewListSource(
    private val theMovieDBApi: TheMovieDBApi,
    private val movieId: Int,
) : PagingSource<Int, ReviewResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response = theMovieDBApi.getMovieReviews(
                movieId = movieId,
                page = pageIndex
            )
            val reviews = response.results
            val nextKey =
                if (reviews.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = reviews,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(
                Exception(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: HttpException) {
            return LoadResult.Error(
                Exception(e.message())
            )
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, ReviewResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}