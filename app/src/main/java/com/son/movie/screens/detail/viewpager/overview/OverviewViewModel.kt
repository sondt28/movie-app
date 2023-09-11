package com.son.movie.screens.detail.viewpager.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class OverviewViewModel @AssistedInject constructor(@Assisted overview: String, val app: Application) :
    AndroidViewModel(app) {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(overview: String): OverviewViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            overview: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(overview) as T
            }
        }
    }

    private val _overviewMovie = MutableLiveData(overview)
    val overviewMovie: LiveData<String> = _overviewMovie
}