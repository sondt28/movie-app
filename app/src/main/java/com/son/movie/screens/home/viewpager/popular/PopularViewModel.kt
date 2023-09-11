package com.son.movie.screens.home.viewpager.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PopularStatus { LOADING, DONE, ERROR }

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _status = MutableLiveData<PopularStatus>()
    val status: LiveData<PopularStatus> = _status

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?> = _navigateToMovieDetails

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _status.value = PopularStatus.LOADING
            try {
                _movies.value = repository.getPopularMoviesAsync()
                _status.value = PopularStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = PopularStatus.ERROR
            }
        }
    }

    fun navigateToMovieDetails(movieId: Int) {
        _navigateToMovieDetails.value = movieId
    }

    fun navigateToMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }
}