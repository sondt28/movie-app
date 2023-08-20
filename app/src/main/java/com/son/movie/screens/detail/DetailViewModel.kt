package com.son.movie.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.son.movie.model.MovieDetails
import com.son.movie.model.MovieTrailer
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieDetailsStatus { LOADING, ERROR, DONE }

class DetailViewModel(val movieId: Int, application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<MovieDetailsStatus>()
    val status: LiveData<MovieDetailsStatus>
        get() = _status

    private val _selectMovie = MutableLiveData<MovieDetails>()
    val selectMovie: LiveData<MovieDetails>
        get() = _selectMovie

    private val _movieTrailer = MutableLiveData<MovieTrailer?>()
    val movieTrailer: LiveData<MovieTrailer?>
        get() = _movieTrailer

    init {
        getMovieDetails()
        getTrailer()
    }

    private fun getMovieDetails() {
        coroutineScope.launch {
            val movieDetailDeferred = MovieApi.retrofitService.getMovieDetailsAsync(movieId)
            _status.value = MovieDetailsStatus.LOADING
            try {
                _selectMovie.value = movieDetailDeferred.await()
                _status.value = MovieDetailsStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = MovieDetailsStatus.ERROR
            }
        }
    }

    private fun getTrailer() {
        coroutineScope.launch {

            try {
                val movieTrailerDeferred = MovieApi.retrofitService.getTrailerMovieAsync(movieId)
                val result = movieTrailerDeferred.await()
                _movieTrailer.value = result.resultVideoMovie.find { it.type == "Trailer" }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}