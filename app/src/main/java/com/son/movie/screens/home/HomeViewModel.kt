package com.son.movie.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import com.son.movie.utils.DataResult
import com.son.movie.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _trendingMoviesDay = MutableLiveData<DataResult<Movies>>()
    val trendingMovieDay: LiveData<DataResult<Movies>> = _trendingMoviesDay

    private val _navigationToSelectFilm = MutableLiveData<Event<Int>>()
    val navigationToSelectFilm: LiveData<Event<Int>> = _navigationToSelectFilm

    init {
        getTrendingMoviesToday()
    }

    fun getTrendingMoviesToday() = viewModelScope.launch {
        DataResult.loading(null)
        try {
            _trendingMoviesDay.value = DataResult.success(repository.getTrendingMovieTodayAsync())
        } catch (t: Throwable) {
            t.printStackTrace()
            DataResult.error(t.message, null)
        }
    }

    fun displayFilmDetails(movieId: Int) {
        _navigationToSelectFilm.value = Event(movieId)
    }
}