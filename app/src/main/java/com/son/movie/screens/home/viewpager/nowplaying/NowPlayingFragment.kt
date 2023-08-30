package com.son.movie.screens.home.viewpager.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.son.movie.databinding.FragmentNowPlayingBinding
import com.son.movie.screens.home.HomeFragmentDirections
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


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvMovies.adapter = MovieItemTypeAdapter(MovieItemTypeAdapter.OnclickItem {
            viewModel.navigateToMovieDetails(it.id)
        })
        handleObserve()

        return binding.root
    }

    private fun handleObserve() {
        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
                viewModel.navigateToMovieDetailsComplete()
            }
        })
    }
}