package dev.baharudin.themoviedb.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baharudin.themoviedb.core.domain.repositories.MovieRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun provideMovieRepository(): MovieRepository
}