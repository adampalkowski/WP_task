package com.example.wp_task.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wp_task.Response
import com.example.wp_task.api.ApiService
import com.example.wp_task.model.Data
import com.example.wp_task.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

const val apiKey= "18ee5e672dmsh3d3d15720669273p1e2b4ejsn72537675e49f"
const val baseUrl= "moviesdatabase.p.rapidapi.com"
const val list_query="most_pop_movies"
const val TAG="MainViewModelDebug"
/**
View-model that handles interaction with the api, fetching data.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _currentMovie = MutableLiveData<Response<Movie>>()
    val currentMovie: LiveData<Response<Movie>> = _currentMovie

    private val movies: MutableList<Movie> = mutableListOf()
    private var currentMovieIndex = 0

    init {
        Log.d(TAG,"inti")

        fetchData()
    }

    private fun fetchData() {
        Log.d(TAG,"fetchData")

        val disposable = apiService.getRandom(
            list_query,
            apiKey,
            baseUrl
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response: Data ->
                    if (response.results.isNotEmpty()) {
                        Log.d(TAG, "Got data size: ${response.results.size}")
                        movies.addAll(response.results)
                        getNextMovie()
                    } else {
                        _currentMovie.postValue(Response.Failure(Exception("Failed to load movies")))
                    }
                },
                { error ->
                    _currentMovie.postValue(Response.Failure(e = java.lang.Exception()))
                }
            )
        disposables.add(disposable)
    }

    fun getNextMovie() {
        Log.d(TAG,"GET NEXT MOVIE")
        if (currentMovieIndex < movies.size) {
            val nextMovie = movies[currentMovieIndex]
            currentMovieIndex++
            _currentMovie.postValue(Response.Success(nextMovie))
        } else {
            fetchData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}