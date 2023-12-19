package dev.baharudin.themoviedb.favorite.di

import dagger.Component
import dev.baharudin.themoviedb.di.FavoriteModuleDependencies
import dev.baharudin.themoviedb.favorite.presentation.FavoriteActivity
import dev.baharudin.themoviedb.favorite.presentation.favorite_movie_list.FavoriteMovieListFragment

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    fun inject(fragment: FavoriteMovieListFragment)

    @Component.Factory
    interface Factory {
        fun create(favoriteModuleDependencies: FavoriteModuleDependencies): FavoriteComponent
    }
}