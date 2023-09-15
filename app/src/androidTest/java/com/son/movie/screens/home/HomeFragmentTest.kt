package com.son.movie.screens.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest {
    @Test
    fun getTrendingMoviesToday_DisplayInUi() {
        val trendingMoviesToday = Movies(1, listOf())
        launchFragmentInContainer<HomeFragment>()
        Thread.sleep(200)
    }
}