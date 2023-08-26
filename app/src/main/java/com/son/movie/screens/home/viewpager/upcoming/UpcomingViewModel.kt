package com.son.movie.screens.home.viewpager.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class UpcomingStatus { LOADING, DONE, ERROR }

class UpcomingViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<UpcomingStatus>()
    val status: LiveData<UpcomingStatus>
        get() = _status

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        coroutineScope.launch {
            Dispatchers.IO
            _status.value = UpcomingStatus.LOADING
            try {
                val movieDeferred = MovieApi.retrofitService.getUpcomingMoviesAsync().await()
                _movies.value = movieDeferred.results
                _status.value = UpcomingStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = UpcomingStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}