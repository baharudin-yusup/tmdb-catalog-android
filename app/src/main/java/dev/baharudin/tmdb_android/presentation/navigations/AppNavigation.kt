package dev.baharudin.tmdb_android.presentation.navigations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationItem(val route: String, val navArgument: List<NamedNavArgument> = listOf()) {
    data object Home : NavigationItem("home")
    data object MovieList : NavigationItem(
        "movies?genreId={genreId}&genreName={genreName}&onlyFavorite={onlyFavorite}", listOf(
            navArgument("genreId") {
                type = NavType.IntType
                defaultValue = 0
            },
            navArgument("genreName") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("onlyFavorite") {
                type = NavType.BoolType
                defaultValue = false
            },
        )
    )

    data object MovieDetail : NavigationItem("movies/{movieId}", listOf(
        navArgument("movieId") {
            type = NavType.IntType
        }
    ))
}

fun String.buildRoute(arguments: Map<String, Any> = mapOf()): String {
    var output = this
    arguments.forEach { (key, value) ->
        output = output.replace("{$key}", "$value")
    }

    output = output.replace("[?&][^&?]*=\\{[^{}]*\\}".toRegex(), "")

    if (output.contains("&") && !output.contains("?")) {
        output = output.replaceFirst("&", "?")
    }

    return output
}