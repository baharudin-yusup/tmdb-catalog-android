package dev.baharudin.tmdb_android.presentation

import dev.baharudin.tmdb_android.presentation.home.HomeScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.tmdb_android.presentation.home.HomeViewModel
import dev.baharudin.tmdb_android.presentation.navigations.AppNavHost
import dev.baharudin.tmdb_android.presentation.ui.theme.TheMovieDBTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FRAGMENT_DESTINATION = "fragment_destination"
        const val EXTRA_PARCELABLE = "parcelable"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        AppNavHost(navController = rememberNavController())
    }
}