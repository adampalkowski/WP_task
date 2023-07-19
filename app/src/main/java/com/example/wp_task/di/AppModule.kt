package com.example.wp_task.di

import android.content.Context
import androidx.room.Room
import com.example.wp_task.api.ApiService
import com.example.wp_task.repo.MovieDao
import com.example.wp_task.repo.MovieDatabase
import com.example.wp_task.repo.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val baseUrl = "https://moviesdatabase.p.rapidapi.com/"

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideContext(   @ApplicationContext
                          context: Context): Context =context

    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase= Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database.db").build()


    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()


    @Provides
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository = MovieRepository(movieDao)


    @Provides
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

