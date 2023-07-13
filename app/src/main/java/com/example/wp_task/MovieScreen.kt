package com.example.wp_task

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.wp_task.model.Movie
import com.example.wp_task.model.MovieData
import com.example.wp_task.roomDb.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenNav(
    onEvent:(MovieEvents)->Unit,
    movie: MutableState<Movie?>,
    movieList: List<MovieData>,
    pageState: PagerState
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        movie.value?.let { nonNullMovie ->
            HorizontalPager(
                state = pageState,
                pageCount = 2,
            ) { page ->
                when (page) {
                    0 -> {
                        MovieDisplayScreen(nonNullMovie,onEvent=onEvent)
                    }
                    1 -> {
                        if (movieList.isNotEmpty()) {
                            FavouriteDisplayScreen(onEvent = onEvent, movieList)
                        } else {
                            Text("No favorites available")
                        }
                    }
                    else -> {

                    }
                }
            }
        } ?: CircularProgressIndicator()
    }
}