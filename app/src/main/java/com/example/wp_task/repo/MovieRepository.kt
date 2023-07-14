package com.example.wp_task.repo

import com.example.wp_task.model.MovieData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


class MovieRepository(private val movieDao: MovieDao) {

    /**
     * Insert a movie into the database.
     *
     * @param movie the movie to be inserted.
     * @return a Completable indicating the success or failure of the insert operation.
     */
    fun insertMovie(movie: MovieData): Completable {
        return movieDao.insertMovie(movie)
    }

    /**
     * Delete a movie from the database.
     *
     * @param movie the movie to be deleted.
     * @return a Completable indicating the success or failure of the delete operation.
     */
    fun deleteMovie(movie: MovieData): Completable {
        return movieDao.deleteMovie(movie)
    }


    /**
     * Retrieve all movies from the database.
     *
     * @return a Flowable emitting a list of movies.
     */
    fun getAllMovies(): Flowable<List<MovieData>> {
        return movieDao.getAllMovies()
    }

}