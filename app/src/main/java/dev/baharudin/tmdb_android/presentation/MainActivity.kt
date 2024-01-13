package dev.baharudin.tmdb_android.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.tmdb_android.presentation.navigations.AppNavHost
import dev.baharudin.tmdb_android.presentation.theme.TheMovieDBTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme {
                Surface {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}