package com.son.movie.screens.home.viewpager.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class PopularStatus { LOADING, DONE, ERROR }

class PopularViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<PopularStatus>()
    val status: LiveData<PopularStatus>
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
            _status.value = PopularStatus.LOADING
            try {
                val movieDeferred = MovieApi.retrofitService.getPopularMoviesAsync().await()
                _movies.value = movieDeferred.results
                _status.value = PopularStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = PopularStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}