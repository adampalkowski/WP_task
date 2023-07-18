package com.example.wp_task.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.wp_task.repo.MovieDao
import com.example.wp_task.repo.MovieDatabase
import com.example.wp_task.repo.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (val application: Application){
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database.db").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieDao: MovieDao): MovieRepository {
        return MovieRepository(movieDao)
    }

}