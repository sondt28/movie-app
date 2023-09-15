package com.son.movie.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.son.movie.getOrAwaitValue
import com.son.movie.repository.FakeMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.TestCase.*


@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var movieRepository: FakeMovieRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        movieRepository = FakeMovieRepository()
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun getTrendingMoviesToDay_success_returnMovies() = runBlocking {
        homeViewModel.getTrendingMoviesToday()
        val value = homeViewModel.trendingMovieDay.getOrAwaitValue()

        assertNotNull(value)
        assertEquals(3, value.results.size)
    }
}

