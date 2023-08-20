package com.son.movie.screens.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.son.movie.model.Movies
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class SearchResultStatus { LOADING, ERROR, DONE }
class SearchViewModel(val query: String, app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _resultSearch = MutableLiveData<Movies>()
    val resultSearch: LiveData<Movies>
        get() = _resultSearch
    init {
        getSearchResult()
    }



    private fun getSearchResult() {
        coroutineScope.launch {
            Dispatchers.IO
            try {
                val searchResultDefferred = MovieApi.retrofitService.getResultSearch(query)
                _resultSearch.value = searchResultDefferred.await()

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