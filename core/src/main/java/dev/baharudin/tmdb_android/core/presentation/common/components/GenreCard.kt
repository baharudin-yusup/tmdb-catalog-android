package dev.baharudin.tmdb_android.core.presentation.common.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baharudin.tmdb_android.core.domain.entities.Genre


@Composable
fun GenreCard(genre: Genre, onClick: (Genre) -> Unit) {
    OutlinedButton(
        contentPadding = PaddingValues.Absolute(
            left = 8.dp,
            right = 8.dp,
            top = 6.dp,
            bottom = 6.dp,
        ),
        modifier = Modifier
            .defaultMinSize(minWidth = ButtonDefaults.MinWidth, minHeight = 30.dp),
        onClick = { onClick(genre) }) {
        Text(
            text = genre.name,
        )
    }
}

@Preview
@Composable
fun PreviewGenreCard() {
    GenreCard(Genre(1, "Action")) {}
}
