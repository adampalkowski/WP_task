package com.example.wp_task

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wp_task.Api.ApiManager
import com.example.wp_task.model.Data
import com.example.wp_task.model.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

const val apiKey= "18ee5e672dmsh3d3d15720669273p1e2b4ejsn72537675e49f"
const val baseUrl= "moviesdatabase.p.rapidapi.com"
const val list_query="most_pop_movies"

class MainViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: MutableState<List<Movie>> = _movies
    private val _currentMovieIndex = mutableStateOf(0)
    private val currentMovieIndex: MutableState<Int> = _currentMovieIndex

    //needed to update the composable ui on dataFetch
    var isDataFetched: MutableState<Response<Boolean>?> = mutableStateOf(null)
    init {

        fetchData()
    }

    fun fetchData() {
        isDataFetched.value = Response.Loading

        val disposable = ApiManager.movieApiService.getRandom(
            list_query,apiKey,baseUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(
                { response: Data ->
                    // Data fetched successfully
                    Log.d("TAG", "GOT DATA size:" + response.entries)
                    _movies.value = response.results
                    isDataFetched.value = Response.Success(true)
                },
                { error ->
                    // Error occurred while fetching data
                    isDataFetched.value = Response.Failure(e = Exception( "Error while fetching data from api."))
                }
            )
        currentMovieIndex.value = 0
        disposables.add(disposable)
    }

    fun getNextMovie(): Movie? {
        val moviesList = movies.value
        if (currentMovieIndex.value < moviesList.size) {
            // Return the next movie and update the index
            val nextMovie = moviesList[currentMovieIndex.value]
            currentMovieIndex.value++
            return nextMovie
        }
        if (currentMovieIndex.value == moviesList.size) {
            // Fetch new data if all movies have been shown
            fetchData()
        }
        return null
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}