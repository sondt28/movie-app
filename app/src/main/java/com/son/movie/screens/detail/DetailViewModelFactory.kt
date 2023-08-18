package com.son.movie.screens.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.son.movie.model.Movie

class DetailViewModelFactory(private val movieId: Int, private val app: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieId, app) as T
        }
        throw IllegalArgumentException("Unknown viewModel")
    }
}