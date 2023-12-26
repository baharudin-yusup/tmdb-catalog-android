package dev.baharudin.themoviedb.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baharudin.themoviedb.core.BuildConfig
import dev.baharudin.themoviedb.core.data.repositories.MovieRepositoryImpl
import dev.baharudin.themoviedb.core.data.sources.local.db.CoreDatabase
import dev.baharudin.themoviedb.core.data.sources.local.db.GenreDao
import dev.baharudin.themoviedb.core.data.sources.local.db.MovieDao
import dev.baharudin.themoviedb.core.data.sources.remote.TheMovieDBApi
import dev.baharudin.themoviedb.core.domain.repositories.MovieRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideCoreDatabase(@ApplicationContext context: Context): CoreDatabase =
        CoreDatabase.create(context)

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        // Create client and add API Key
        val client = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).addInterceptor { chain ->
                val request = chain.request().newBuilder().url(chain.request().url)
                    .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY).build()

                chain.proceed(request)
            }

        // Add interceptors
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            client.addInterceptor(httpLoggingInterceptor)
        }

        return client.build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()

    @Provides
    @Singleton
    fun provideGenreDao(coreDatabase: CoreDatabase): GenreDao = coreDatabase.genreDao()

    @Provides
    @Singleton
    fun provideMovieDao(coreDatabase: CoreDatabase): MovieDao = coreDatabase.movieDao()

    @Provides
    @Singleton
    fun provideTheMovieDBApi(retrofit: Retrofit): TheMovieDBApi =
        retrofit.create(TheMovieDBApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(
        genreDao: GenreDao, movieDao: MovieDao, theMovieDBApi: TheMovieDBApi
    ): MovieRepository = MovieRepositoryImpl(
        genreDao = genreDao,
        movieDao = movieDao,
        theMovieDBApi = theMovieDBApi,
    )
}