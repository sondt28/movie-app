package com.son.movie.screens.home.viewpager.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.son.movie.databinding.FragmentNowPlayingBinding
import com.son.movie.screens.home.MovieItemTypeAdapter

class NowPlayingFragment : Fragment() {
    private lateinit var binding: FragmentNowPlayingBinding
    private val viewModel: NowPlayingViewModel by lazy {
        ViewModelProvider(this)[NowPlayingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(inflater)
        binding.rvMovies.adapter = MovieItemTypeAdapter()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}