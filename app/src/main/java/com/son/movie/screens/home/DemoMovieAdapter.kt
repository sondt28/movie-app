package com.son.movie.screens.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.son.movie.screens.home.viewpager.nowplaying.NowPlayingFragment
import com.son.movie.screens.home.viewpager.popular.PopularFragment
import com.son.movie.screens.home.viewpager.toprated.TopRatedFragment
import com.son.movie.screens.home.viewpager.upcoming.UpcomingFragment

class DemoMovieAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> {
                UpcomingFragment()
            }
            2 -> {
                TopRatedFragment()
            }
            3 -> {
                PopularFragment()
            }
            else -> {
                NowPlayingFragment()
            }
        }
    }
}