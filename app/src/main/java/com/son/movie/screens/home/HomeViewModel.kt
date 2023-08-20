package com.son.movie.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.son.movie.database.getDatabase
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import com.son.movie.repository.MovieRepository
import com.son.movie.utils.MovieStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<MovieStatus>()
    val status: LiveData<MovieStatus>
        get() = _status

    private val _trendingMoviesDay = MutableLiveData<List<Movie>>()
    val trendingMovieDay: LiveData<List<Movie>>
        get() = _trendingMoviesDay

    private val _navigationToSelectFilm = MutableLiveData<Int?>()
    val navigationToSelectFilm: LiveData<Int?>
        get() = _navigationToSelectFilm

    private val database = getDatabase(application)
    private val movieRepository = MovieRepository(database)

    val movies = movieRepository.movies
    init {
        getTrendingMoviesToday()
    }

    //    private fun getTrendingMoviesToday() {
//        coroutineScope.launch {
//            Dispatchers.IO
//            val moviesDeferred = MovieApi.retrofitService.getTrendingMoviesTodayAsync()
//
//            try {
//                _status.value = MovieStatus.LOADING
//                val listResult = moviesDeferred.await()
//                _status.value = MovieStatus.DONE
//
//                _trendingMoviesDay.value = listResult.results
//            } catch (t: Throwable) {
//                t.printStackTrace()
//                _status.value = MovieStatus.ERROR
//            }
//        }
//    }
    private fun getTrendingMoviesToday() {
        coroutineScope.launch {
            movieRepository.refreshMovies()
        }
    }

    fun displayFilmDetails(movieId: Int) {
        _navigationToSelectFilm.value = movieId
    }

    fun displayFilmDetailsComplete() {
        _navigationToSelectFilm.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}