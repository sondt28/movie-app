package com.son.movie.screens.home.viewpager.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class NowPlayingStatus { LOADING, DONE, ERROR }

class NowPlayingViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<NowPlayingStatus>()
    val status: LiveData<NowPlayingStatus>
        get() = _status

    private val _movies = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = _movies

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        coroutineScope.launch {
            try {
                _status.value = NowPlayingStatus.LOADING
                val moviesDeferred = MovieApi.retrofitService.getNowPlayingMoviesAsync().await()
                _movies.value = moviesDeferred.results

                _status.value = NowPlayingStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = NowPlayingStatus.ERROR
            }
        }
    }
}