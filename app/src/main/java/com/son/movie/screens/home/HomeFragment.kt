package com.son.movie.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.son.movie.R
import com.son.movie.databinding.FragmentHomeBinding
import com.son.movie.screens.home.viewpager.DemoMovieAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var demoMovieAdapter: DemoMovieAdapter

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(activity).application
        val homeViewModelFactory = HomeViewModelFactory(activity)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupTrendingMoviesTodayRecyclerView()
        setupTagLayout()
        handleObserver()

        return binding.root
    }

    private fun setupTrendingMoviesTodayRecyclerView() {
        binding.rvTrendingMoviesToday.adapter = MovieItemAdapter(MovieItemAdapter.OnclickListener {
            viewModel.displayFilmDetails(it.id)
        })
    }

    private fun handleObserver() {
        viewModel.navigationToSelectFilm.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)

                viewModel.displayFilmDetailsComplete()
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