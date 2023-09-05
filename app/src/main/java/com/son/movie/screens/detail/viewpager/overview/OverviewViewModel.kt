package com.son.movie.screens.detail.viewpager.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OverviewViewModel(overview: String, val app: Application) : AndroidViewModel(app) {

    private val _overviewMovie = MutableLiveData(overview)
    val overviewMovie: LiveData<String>
        get() = _overviewMovie

}