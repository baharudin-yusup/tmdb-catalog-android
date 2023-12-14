package dev.baharudin.themoviedb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baharudin.themoviedb.BuildConfig
import dev.baharudin.themoviedb.data.repositories.MovieRepositoryImpl
import dev.baharudin.themoviedb.data.sources.local.db.AppDatabase
import dev.baharudin.themoviedb.data.sources.local.db.GenreDao
import dev.baharudin.themoviedb.data.sources.local.db.MovieDao
import dev.baharudin.themoviedb.data.sources.remote.TheMovieDBApi
import dev.baharudin.themoviedb.domain.repositories.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).addInterceptor { chain ->
                val request = chain.request().newBuilder().url(chain.request().url)
                    .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY).build()

                chain.proceed(request)
            }

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
    fun provideGenreDao(appDatabase: AppDatabase): GenreDao = appDatabase.genreDao()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

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