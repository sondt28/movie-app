package com.son.movie.screens.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.son.movie.databinding.FragmentWatchListBinding
import timber.log.Timber

class WatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchListBinding

    private val viewModel: WatchlistViewModel by lazy {
        ViewModelProvider(this)[WatchlistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView Called")
        binding = FragmentWatchListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getOwnWatchlist()

        setupAdapter()
        setupObserver()

        return binding.root
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