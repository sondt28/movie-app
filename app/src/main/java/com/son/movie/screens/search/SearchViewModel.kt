package com.son.movie.screens.search

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
class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _resultSearch = MutableLiveData<DataResult<Movies>>()
    val resultSearch: LiveData<DataResult<Movies>> = _resultSearch

    private val _navigateToMovieDetails = MutableLiveData<Event<Int>>()
    val navigateToMovieDetails: LiveData<Event<Int>> = _navigateToMovieDetails

    fun getSearchResult(query: String) {
        viewModelScope.launch {
            DataResult.loading(null)
            try {
                _resultSearch.value = DataResult.success(repository.getResultSearchAsync(query))
            } catch (t: Throwable) {
                t.printStackTrace()
                DataResult.error(t.message, null)
            }
        }
    }

    fun displayDetailsFilm(movieId: Int) {
        _navigateToMovieDetails.value = Event(movieId)
    }
}