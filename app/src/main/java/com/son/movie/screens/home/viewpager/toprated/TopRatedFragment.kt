package com.son.movie.screens.home.viewpager.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.son.movie.R
import com.son.movie.databinding.FragmentPopularBinding
import com.son.movie.databinding.FragmentTopRatedBinding
import com.son.movie.screens.home.HomeFragmentDirections
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
        handleObserve()
        binding.rvMovies.adapter = MovieItemTypeAdapter(MovieItemTypeAdapter.OnclickItem {
            viewModel.navigateToMovieDetails(it.id)
        })
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