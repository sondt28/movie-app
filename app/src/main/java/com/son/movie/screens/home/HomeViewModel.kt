package com.son.movie.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class MovieStatus { LOADING, ERROR, DONE }

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _status = MutableLiveData<MovieStatus>()
    val status: LiveData<MovieStatus> = _status

    private val _trendingMoviesDay = MutableLiveData<Movies>()
    val trendingMovieDay: LiveData<Movies> = _trendingMoviesDay

    private val _navigationToSelectFilm = MutableLiveData<Int?>()
    val navigationToSelectFilm: LiveData<Int?> = _navigationToSelectFilm

    init {
        getTrendingMoviesToday()
    }

    fun getTrendingMoviesToday() {
        viewModelScope.launch {
            _status.value = MovieStatus.LOADING
            try {
                val movies = repository.getTrendingMovieTodayAsync()
                _trendingMoviesDay.value = movies
                _status.value = MovieStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = MovieStatus.ERROR
            }
        }
    }

    fun displayFilmDetails(movieId: Int) {
        _navigationToSelectFilm.value = movieId
    }

    fun displayFilmDetailsCompleted() {
        _navigationToSelectFilm.value = null
    }
}