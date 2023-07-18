package com.example.wp_task.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wp_task.MyApp
import com.example.wp_task.model.MovieData
import com.example.wp_task.repo.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject


/**
 * View Model that handles interaction with room database, to store favorites.
 */
class FavouritesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    // BehaviorSubject to hold the list of movies
    val movies: BehaviorSubject<List<MovieData>> = BehaviorSubject.createDefault(emptyList())

    private val disposables = CompositeDisposable()
    private val _toastState: MutableLiveData<String> = MutableLiveData()
    val toastState: LiveData<String> = _toastState

    init {
     fetchMovies()
    }

    // Clear the toast state
    fun clearToastState() {
        _toastState.value = null
    }

    // Fetch movies from the repository
    private fun fetchMovies() {
        movieRepository.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    movies.onNext(list)
                },
                { error ->
                    handleFetchMoviesError()
                }
            )
            .let { disposables.add(it) }
    }

    // Handle the error when fetching movies
    private fun handleFetchMoviesError() {
        _toastState.value = "Failed to fetch movies"
    }
    // Insert a movie into the repository
    fun insertMovie(movie: MovieData) {
        movieRepository.insertMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    fetchMovies()
                    _toastState.value = "Movie added to favorites"
                },
                { error ->
                    _toastState.value = "Failed to add movie"
                }
            )
            .let { disposables.add(it) }
    }

    // Delete a movie from the repository
    fun deleteMovie(movie: MovieData) {
        movieRepository.deleteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    fetchMovies()
                    _toastState.value = "Movie removed from favorites"
                },
                { error ->
                    _toastState.value = "Failed to remove movie"
                }
            )
            .let { disposables.add(it) }
    }

    // Clean up resources
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}