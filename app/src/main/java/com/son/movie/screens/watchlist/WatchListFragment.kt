package com.son.movie.screens.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.son.movie.databinding.FragmentWatchListBinding
import com.son.movie.utils.bindImage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
        }
    }

    private fun setupAdapter() {
        binding.rvMovies.adapter = WatchlistItemAdapter(WatchlistItemAdapter.OnclickItem {
            viewModel.navigateToMovieDetail(it.id)
        })
    }

    private fun setupObserver() {
        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = WatchListFragmentDirections.actionWatchListFragmentToDetailFragment(it)
                findNavController().navigate(action)
                viewModel.doneNavigateToMovieDetail()
            }
        })
    }
}