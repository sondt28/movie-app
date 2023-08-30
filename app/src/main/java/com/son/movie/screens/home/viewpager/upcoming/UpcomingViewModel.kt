package com.son.movie.screens.home.viewpager.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
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

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails:LiveData<Int?>
        get() = _navigateToMovieDetails


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

    fun navigateToMovieDetails(movieId: Int) {
        _navigateToMovieDetails.value = movieId
    }

    fun navigateToMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}