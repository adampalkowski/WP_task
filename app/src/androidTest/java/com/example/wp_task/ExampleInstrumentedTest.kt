package com.example.wp_task

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wp_task.model.MovieData
import com.example.wp_task.repo.MovieDao
import com.example.wp_task.repo.MovieDatabase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieDaoTests {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadMovies() {
        val movie = MovieData("1","TEST")
        println(movie.toString())
        movieDao.insertMovie(movie)
            .test()
            .assertComplete()

        // Optionally, retrieve the inserted movie and verify its correctness
        val insertedMovie = movieDao.getAllMovies().blockingFirst().firstOrNull()
        println(insertedMovie.toString())

        assertNotNull(insertedMovie)
        assertEquals(movie._id, insertedMovie?._id)
        assertEquals(movie.titleText, insertedMovie?.titleText)
    }
    @Test
    @Throws(Exception::class)
    fun deleteMovie_shouldDeleteMovie() {
        // Insert a movie to be deleted
        val movie = MovieData("1", "TEST")
        movieDao.insertMovie(movie)
            .test()
            .assertComplete()
        val insertedMovie = movieDao.getAllMovies().blockingFirst().firstOrNull()
        println(insertedMovie.toString())

        // Delete the movie
        movieDao.deleteMovie(movie)
            .test()
            .assertComplete()

        // Verify that the movie is deleted by retrieving all movies
        val movies = movieDao.getAllMovies().blockingFirst()

        // Verify that the list is empty
        assertTrue(movies.isEmpty())
    }
    @Test
    @Throws(Exception::class)
    fun getAllMovies_shouldReturnAllMovies() {
        // Prepare a list of movies
        val movies = listOf(
            MovieData("1", "Movie 1"),
            MovieData("2", "Movie 2"),
            MovieData("3", "Movie 3")
        )

        // Insert the movies into the database
        movieDao.insertMovie(*movies.toTypedArray())
            .test()
            .assertComplete()

        // Retrieve all movies
        val retrievedMovies = movieDao.getAllMovies().blockingFirst()

        // Verify that the retrieved movies match the inserted movies
        assertEquals(movies.size, retrievedMovies.size)
        assertTrue(retrievedMovies.containsAll(movies))
    }
}