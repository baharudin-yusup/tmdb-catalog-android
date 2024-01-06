package dev.baharudin.tmdb_android.favorite.di

import dagger.Component
import dev.baharudin.tmdb_android.di.FavoriteModuleDependencies
import dev.baharudin.tmdb_android.favorite.presentation.FavoriteActivity
import dev.baharudin.tmdb_android.favorite.presentation.favorite_movie_list.FavoriteMovieListFragment

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    fun inject(fragment: FavoriteMovieListFragment)

    @Component.Factory
    interface Factory {
        fun create(favoriteModuleDependencies: FavoriteModuleDependencies): FavoriteComponent
    }
}