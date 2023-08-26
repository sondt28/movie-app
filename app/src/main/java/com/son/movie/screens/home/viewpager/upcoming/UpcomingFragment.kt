package com.son.movie.screens.home.viewpager.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.son.movie.databinding.FragmentUpcomingBinding
import com.son.movie.screens.home.DemoMovieAdapter
import com.son.movie.screens.home.MovieItemTypeAdapter

class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: UpcomingViewModel by lazy {
        ViewModelProvider(this)[UpcomingViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rcMovies.adapter = MovieItemTypeAdapter()

        return binding.root
    }
}