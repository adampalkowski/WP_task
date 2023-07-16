package com.example.wp_task.repo

import android.content.Context


/**
 * Enables injection of data source.
 */
object Injection {

    fun provideUserDataSource(context: Context): MovieDao {
        val database = MovieDatabase.getInstance(context)
        return database.movieDao()
    }

}