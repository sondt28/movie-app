package com.son.movie.screens.detail.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.son.movie.screens.detail.viewpager.cast.CastFragment
import com.son.movie.screens.detail.viewpager.overview.OverviewFragment
import com.son.movie.screens.detail.viewpager.reviews.ReviewsFragment

class DemoMovieDetailAdapter(fragment: Fragment, private val overview: String?) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = OverviewFragment()
                val args = Bundle()
                args.putString("overview", overview)
                fragment.arguments = args
                fragment
            }
            1 -> ReviewsFragment()
            2 -> CastFragment()
            else -> Fragment()
        }
    }
}