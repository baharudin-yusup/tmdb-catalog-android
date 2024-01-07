package dev.baharudin.tmdb_android.presentation.screens.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.domain.entities.Review
import dev.baharudin.tmdb_android.core.presentation.common.components.RetrySection
import dev.baharudin.tmdb_android.core.presentation.common.components.ReviewCard

@Composable
fun MovieReview(reviews: LazyPagingItems<Review>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reviews.itemCount) { index ->
                val review = checkNotNull(reviews[index])
                ReviewCard(review)
            }
        }

        when {
            reviews.loadState.refresh is LoadState.Error -> {
                RetrySection(
                    onRetryClick = { /*TODO: Add this*/ },
                    errorMessage = ""
                    // TODO: Handle this
//                    errorMessage = (reviews.loadState.refresh as LoadState.Error).error.message
                )
            }

            reviews.itemCount == 0 -> {
                Text(
                    text = stringResource(id = R.string.no_reviews),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}