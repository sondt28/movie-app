package com.son.movie.screens.home.viewpager.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.son.movie.R
import com.son.movie.databinding.FragmentPopularBinding
import com.son.movie.databinding.FragmentTopRatedBinding
import com.son.movie.screens.home.MovieItemTypeAdapter
import com.son.movie.screens.home.viewpager.popular.PopularViewModel

class TopRatedFragment : Fragment() {
    private lateinit var binding: FragmentTopRatedBinding
    private val viewModel: TopRatedViewModel by lazy {
        ViewModelProvider(this)[TopRatedViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopRatedBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvMovies.adapter = MovieItemTypeAdapter()
        return binding.root
    }

}