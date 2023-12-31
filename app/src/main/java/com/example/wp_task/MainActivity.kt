package com.example.wp_task

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wp_task.model.Movie
import com.example.wp_task.model.MovieData
import com.example.wp_task.screens.MovieEvents
import com.example.wp_task.screens.ScreenNav
import com.example.wp_task.ui.theme.WP_taskTheme
import com.example.wp_task.viewModels.FavouritesViewModel
import com.example.wp_task.viewModels.MainViewModel
import com.example.wp_task.viewModels.TAG
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val favouritesViewModel by viewModels<FavouritesViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WP_taskTheme {
                var movie by remember { mutableStateOf<Movie?>(null) }
                var movieList by remember { mutableStateOf<List<MovieData>>(emptyList()) }
                movieList = favouritesViewModel.movies.value as List<MovieData>

                /*Main screen*/
                //with no internet connection can't display the favourites screen
                movie?.let { nonNullMovie ->
                    ScreenNav(
                        onEvent = { event ->
                            when (event) {
                                is MovieEvents.NewMovie -> {
                                    mainViewModel.getNextMovie()
                                }
                                is MovieEvents.AddToFavourite -> {
                                    val movieData = MovieData(
                                        _id = nonNullMovie._id,
                                        titleText = nonNullMovie.titleText.text
                                    )
                                    favouritesViewModel.insertMovie(movieData)
                                }
                                is MovieEvents.UnLike -> {
                                    val movieData = MovieData(_id = event.id, titleText = event.title)
                                    favouritesViewModel.deleteMovie(movieData)
                                }
                            }
                        },
                        movie = nonNullMovie,
                        movieList = movieList,
                    )
                } ?: run {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

               /*Display toast on insert or delete*/
                val toastMessage: String? by favouritesViewModel.toastState.observeAsState()
                if (toastMessage != null) {
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                    favouritesViewModel.clearToastState()
                }
                /*Pass the movie to ui*/
                mainViewModel.currentMovie.observe(
                    this
                ) {
                    when (it) {
                        is Response.Success -> {
                            // Update the current movie
                            movie = it.data
                        }
                        is Response.Failure -> {
                            // Handle failure, fetch new movie
                            Log.d(TAG,"Failure")

                            mainViewModel.getNextMovie()
                        }
                        is Response.Loading -> {
                            // Handle loading
                        }
                    }
                }

            }
        }
    }
}


