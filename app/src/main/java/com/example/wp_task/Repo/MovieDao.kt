package com.example.wp_task.Repo


import androidx.room.*
import com.example.wp_task.model.MovieData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MovieDao {
    @Insert
    fun insertMovie(vararg movie: MovieData): Completable

    @Delete
    fun deleteMovie(movie: MovieData): Completable

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flowable<List<MovieData>>

}