package com.example.wp_task.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wp_task.api.ApiManager
import com.example.wp_task.Response
import com.example.wp_task.model.Data
import com.example.wp_task.model.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

const val apiKey= "18ee5e672dmsh3d3d15720669273p1e2b4ejsn72537675e49f"
const val baseUrl= "moviesdatabase.p.rapidapi.com"
const val list_query="most_pop_movies"
const val TAG="MainViewModelDebug"
/**
View-model that handles interaction with the api, fetching data.
 */
class MainViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    private val movies: MutableState<List<Movie>> = _movies
    private val _currentMovieIndex = mutableStateOf(0)
    private val currentMovieIndex: MutableState<Int> = _currentMovieIndex

    private val _currentMovie = MutableLiveData<Response<Movie>>()
    val currentMovie: LiveData<Response<Movie>> = _currentMovie

    init {
        fetchData()
    }

    // Fetch data from API
    private fun fetchData() {
        val disposable = ApiManager.movieApiService.getRandom(
            list_query,
            apiKey,
            baseUrl
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(
                { response: Data ->
                    // Data fetched successfully
                    Log.d(TAG, "Got data size:" + response.entries)
                    _movies.value = response.results
                    _currentMovieIndex.value = 0 // Reset the index
                    getNextMovie()
                },
                {error->
                    _currentMovie.postValue(Response.Failure(e=Exception( "Failed to load movies")))
                }
            )
        disposables.add(disposable)
    }

    // Get the next movie from the list
    fun getNextMovie() {
        _currentMovie.postValue(Response.Loading)

        val moviesList = movies.value
        if (currentMovieIndex.value < moviesList.size) {
            // Return the next movie and update the index
            val nextMovie = moviesList[currentMovieIndex.value]
            currentMovieIndex.value++
            _currentMovie.postValue(Response.Success(nextMovie))
        } else if (currentMovieIndex.value == moviesList.size) {
            // Fetch new data if all movies have been shown
            fetchData()
        }
    }

    // Clean up resources
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}