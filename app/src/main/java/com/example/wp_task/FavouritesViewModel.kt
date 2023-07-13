package com.example.wp_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wp_task.model.MovieData
import com.example.wp_task.roomDb.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class FavouritesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val movies: BehaviorSubject<List<MovieData>> = BehaviorSubject.createDefault(emptyList())
    private val disposables = CompositeDisposable()
    private val _toastState: MutableLiveData<String> = MutableLiveData()
    val toastState: LiveData<String> = _toastState

    init {
        fetchMovies()
    }

     fun clearToastState() {
         _toastState.value=null
     }
         fun fetchMovies() {

        movieRepository.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    movies.onNext(list)
                },
                { error ->
                    // Handle error
                }
            )
            .let { disposables.add(it) }
    }

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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}