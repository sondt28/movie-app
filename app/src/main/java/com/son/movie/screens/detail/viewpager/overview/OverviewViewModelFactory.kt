package com.son.movie.screens.detail.viewpager.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OverviewViewModelFactory(private val overview: String, private val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(overview, app) as T
        }
    throw IllegalArgumentException("Unknown view model")
    }
}