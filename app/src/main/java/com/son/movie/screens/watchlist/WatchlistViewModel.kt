package com.son.movie.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class WatchListStatsus { LOADING, ERROR, DONE }

class WatchlistViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<WatchListStatsus>()
    val status: LiveData<WatchListStatsus>
        get() = _status

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies>
        get() = _movies

    private val _navigateToMovieDetail = MutableLiveData<Int?>()
    val navigateToMovieDetail: LiveData<Int?>
        get() = _navigateToMovieDetail

     fun getOwnWatchlist() {
        Timber.i("getOwnWatchlist() Called")
        coroutineScope.launch {
            _status.value = WatchListStatsus.LOADING
            try {
                val moviesDeferred = MovieApi.retrofitService.getWatchlistMoviesAsync().await()
                _movies.value = moviesDeferred
                _status.value = WatchListStatsus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = WatchListStatsus.ERROR
            }
        }
    }

    fun navigateToMovieDetail(movieId: Int) {
        _navigateToMovieDetail.value = movieId
    }

    fun doneNavigateToMovieDetail() {
        _navigateToMovieDetail.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}