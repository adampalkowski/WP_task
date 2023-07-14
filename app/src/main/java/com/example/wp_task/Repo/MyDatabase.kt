package com.example.wp_task.Repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wp_task.model.MovieData

@Database(entities = [MovieData::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}