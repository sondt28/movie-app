package com.son.movie.screens.home.viewpager.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.son.movie.R
import com.son.movie.databinding.FragmentPopularBinding
import com.son.movie.databinding.FragmentUpcomingBinding
import com.son.movie.screens.home.MovieItemTypeAdapter
import com.son.movie.screens.home.viewpager.upcoming.UpcomingViewModel

class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by lazy {
        ViewModelProvider(this)[PopularViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvMovies.adapter = MovieItemTypeAdapter()
        return binding.root
    }
}