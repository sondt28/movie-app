package com.son.movie.screens.home.viewpager.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import com.son.movie.screens.home.viewpager.upcoming.UpcomingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class TopRatedStatus { LOADING, DONE, ERROR }

class TopRatedViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<TopRatedStatus>()
    val status: LiveData<TopRatedStatus>
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
            _status.value = TopRatedStatus.LOADING
            try {
                val movieDeferred = MovieApi.retrofitService.getTopRatedMoviesAsync().await()
                _movies.value = movieDeferred.results
                _status.value = TopRatedStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = TopRatedStatus.ERROR
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