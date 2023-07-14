package com.example.wp_task.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.wp_task.FavouriteDisplayScreen
import com.example.wp_task.MovieDisplayScreen
import com.example.wp_task.model.Movie
import com.example.wp_task.model.MovieData

sealed class MovieEvents {
    /**
     * Event of unliking a movie, needs params to delete the MovieData from Room database.
     *
     * @param id the ID of the movie.
     * @param title the title of the movie.
     */
    class UnLike(val id: String, val title: String) : MovieEvents()

    /**
     * Event of clicking refresh button.
     */
    object NewMovie : MovieEvents()

    /**
     * Event of clicking heart button.
     */
    object AddToFavourite : MovieEvents()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenNav(
    onEvent: (MovieEvents) -> Unit,
    movie: Movie,
    movieList: List<MovieData>,
) {
    val pageState = rememberPagerState()
    //check if displayed movie is liked
    val favourite = movieList.any { movieData ->
        movieData._id == movie._id
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        HorizontalPager(
            state = pageState,
            pageCount = 2,
        ) { page ->
            when (page) {
                0 -> {

                    MovieDisplayScreen(movie, onEvent = onEvent, favourite = favourite)
                }
                1 -> {
                    if (movieList.isNotEmpty()) {
                        FavouriteDisplayScreen(onEvent = onEvent, movieList)
                    } else {
                        Box(Modifier.fillMaxSize()) {
                            Text("No favorites available")

                        }
                    }
                }
            }
        }
    }
}