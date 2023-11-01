package com.son.movie.screens.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.son.movie.R
import com.son.movie.databinding.FragmentWatchListBinding
import com.son.movie.utils.Animation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchListBinding
    private val viewModel: WatchlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initView()
        setupAdapter()
        setupObserver()

        return binding.root
    }

    private fun initView() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getOwnWatchlist()
            binding.swipeRefresh.isRefreshing = false
            binding.swipeRefresh.setColorSchemeColors(resources.getColor(R.color.fresh_blue))
        }
    }

    private fun setupAdapter() {
        val watchlistItemAdapter = WatchlistItemAdapter(WatchlistItemAdapter.OnclickItem {
            viewModel.navigateToMovieDetail(it.id)
        })

        binding.rvMovies.apply {
            adapter = watchlistItemAdapter
            layoutAnimation = Animation.setAnimation(requireContext())
        }
    }

    private fun setupObserver() {
        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { movieId ->
                val action =
                    WatchListFragmentDirections.actionWatchListFragmentToDetailFragment(movieId)
                findNavController().navigate(action)
            }
        })
    }
}