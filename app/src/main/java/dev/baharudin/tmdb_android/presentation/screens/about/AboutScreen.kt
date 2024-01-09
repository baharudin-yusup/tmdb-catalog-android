package dev.baharudin.tmdb_android.presentation.screens.about

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import dev.baharudin.tmdb_android.R

val githubLightIcon = compositionLocalOf { R.drawable.ic_github_black }
val githubDarkIcon = compositionLocalOf { R.drawable.ic_github_white }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {

    val githubIcon = if (isSystemInDarkTheme()) {
        githubDarkIcon
    } else {
        githubLightIcon
    }

    val context = LocalContext.current

    val openUrlLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.about_screen_top_appbar_title))
            })
        },
        floatingActionButton = {
            CompositionLocalProvider {
                FloatingActionButton(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    onClick = {
                        val url = "https://github.com/baharudin-yusup"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        openUrlLauncher.launch(intent)
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = githubIcon.current),
                            contentDescription = null,
                        )
                    })
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(100.dp, 100.dp),
                model = "https://github.com/baharudin-yusup/baharudin-yusup/blob/main/assets/images/user-picture.png?raw=true",
                contentDescription = stringResource(id = R.string.developer_profile_picture),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )

            Text(
                modifier = Modifier.padding(vertical = 24.dp),
                text = stringResource(id = R.string.developer_name),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen()
}