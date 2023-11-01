package com.son.movie.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.son.movie.getOrAwaitValue
import com.son.movie.model.Genre
import com.son.movie.model.Movie
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
        val fakeGenre = Genre(
            id = 1,
            name = "Action"
        )

        val fakeMovie = Movie(
            id = 1234,
            title = "Sample Movie",
            posterPath = "/sample_poster.jpg",
            releaseDate = "2023-09-13",
            backdropPath = "/sample_backdrop.jpg",
            overview = "This is a sample movie overview.",
            runtime = 120,
            genres = listOf(fakeGenre),
            voteAverage = 7.5f
        )

        val fakeMovie1 = Movie(
            id = 5678,
            title = "Another Sample Movie 1",
            posterPath = "/sample_poster_1.jpg",
            releaseDate = "2023-09-14",
            backdropPath = "/sample_backdrop_1.jpg",
            overview = "This is another sample movie overview 1.",
            runtime = 110,
            genres = listOf(fakeGenre),
            voteAverage = 8.0f
        )

        val fakeMovie2 = Movie(
            id = 9101,
            title = "Another Sample Movie 2",
            posterPath = "/sample_poster_2.jpg",
            releaseDate = "2023-09-15",
            backdropPath = "/sample_backdrop_2.jpg",
            overview = "This is another sample movie overview 2.",
            runtime = 105,
            genres = listOf(fakeGenre),
            voteAverage = 7.2f
        )

        movieRepository.addMovies(fakeMovie, fakeMovie1, fakeMovie2)
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun getTrendingMoviesToDay_success_returnMovies() = runBlocking {
        homeViewModel.getTrendingMoviesToday()
        val value = homeViewModel.trendingMovieDay.getOrAwaitValue()

        assertNotNull(value)
    }

    @Test
    fun getTrendingMoviesToDay_error_returnMovies() = runBlocking {
        homeViewModel.getTrendingMoviesToday()
        val value = homeViewModel.trendingMovieDay.getOrAwaitValue()

        assertNotNull(value)
    }
}

