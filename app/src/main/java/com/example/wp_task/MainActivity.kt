package com.example.wp_task

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.wp_task.roomDb.MovieDatabase
import com.example.wp_task.roomDb.MovieRepository
import com.example.wp_task.model.Movie
import com.example.wp_task.model.MovieData
import com.example.wp_task.ui.theme.WP_taskTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

const val TAG ="MAINACTIVITYDEBUG"
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(this, MovieDatabase::class.java, "movie_database.db")
            .build()
        val movieDao = database.movieDao()
        val movieRepository = MovieRepository(movieDao)
         val favouritesViewModel=FavouritesViewModel(movieRepository = movieRepository)

        setContent {
            WP_taskTheme {

                var movie = remember{ mutableStateOf<Movie?>(null) }
                val pageState = rememberPagerState()
                /*retrieve favourites from database with RxJava*/
                var movieList by remember { mutableStateOf<List<MovieData>>(emptyList()) }
                movieList=favouritesViewModel.movies.value
                ScreenNav(
                 onEvent = { event ->
                        when (event) {
                            is MovieEvents.NewMovie -> {
                                mainViewModel.getNextMovie()?.let { nextMovie ->
                                    movie.value=nextMovie
                                }
                            }
                            is MovieEvents.AddToFavourite -> {
                                val movieData = MovieData(
                                    _id = movie.value?._id.toString(),
                                    titleText = movie.value?.titleText?.text.toString()
                                )
                                favouritesViewModel.insertMovie(movieData)

                            }
                            is MovieEvents.UnLike->{
                                val movieData =
                                    MovieData(_id = event.id, titleText = event.title)
                                favouritesViewModel.deleteMovie(movieData)
                            }
                        }
                    }   ,
                    movie = movie,
                    movieList = movieList,
                    pageState =pageState
                )
                val toastMessage: String? by favouritesViewModel.toastState.observeAsState()
                if (toastMessage != null) {
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                    favouritesViewModel.clearToastState()
                }
                mainViewModel.isDataFetched.value.let {response->
                    when(response){
                        is Response.Success->{
                            mainViewModel.getNextMovie()?.let { nextMovie ->
                                movie.value=nextMovie
                            }
                            mainViewModel.isDataFetched.value=null
                        }
                        else->{

                        }
                    }
                }
            }
        }
    }
}


