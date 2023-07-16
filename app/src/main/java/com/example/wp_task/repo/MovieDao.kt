package com.example.wp_task.repo


import androidx.room.*
import com.example.wp_task.model.MovieData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
/**
 * Data Access Object for the movie table.
 */
@Dao
interface MovieDao {
    /**
     * Insert a movie in the database.

     * @param movie the movie to be inserted.
     */
    @Insert
    fun insertMovie(vararg movie: MovieData): Completable
    /**
     * Delete a movie from the database.

     * @param movie the movie to be deleted.
     */
    @Delete
    fun deleteMovie(movie: MovieData): Completable
    /**
     * Get all movies.

     * @return all movies.
     */
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flowable<List<MovieData>>

}