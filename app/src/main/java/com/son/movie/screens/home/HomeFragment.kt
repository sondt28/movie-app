package com.son.movie.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.son.movie.R
import com.son.movie.databinding.FragmentHomeBinding
import com.son.movie.screens.home.viewpager.DemoMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var demoMovieAdapter: DemoMovieAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupTagLayout()
        handleObserver()

        return binding.root
    }

    private fun setupRecyclerView() {
        val movieItemAdapter =  MovieItemAdapter(MovieItemAdapter.OnclickListener {
            viewModel.displayFilmDetails(it.id)
        })
        movieItemAdapter.setHasStableIds(true)

        binding.rvTrendingMoviesToday.apply {
            adapter = movieItemAdapter
        }
    }

    private fun handleObserver() {
        viewModel.navigationToSelectFilm.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { movieId ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
                findNavController().navigate(action)
            }
        })
    }

    private fun setupTagLayout() {
        demoMovieAdapter = DemoMovieAdapter(this)
        binding.pager.adapter = demoMovieAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.now_playing)
                1 -> tab.text = getString(R.string.upcoming)
                2 -> tab.text = getString(R.string.top_rated)
                3 -> tab.text = getString(R.string.popular)
            }
        }.attach()
    }
}