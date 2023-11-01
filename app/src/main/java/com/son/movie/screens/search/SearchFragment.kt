package com.son.movie.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.son.movie.databinding.FragmentSearchBinding
import com.son.movie.utils.Animation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchItemAdapter: SearchItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        searchItemAdapter = SearchItemAdapter(SearchItemAdapter.OnClickListener {
            viewModel.displayDetailsFilm(it.id)
        })

        initView()
        handleSearch()
        handleObserver()

        return binding.root
    }

    private fun handleObserver() {
        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { movieId ->
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId)
                findNavController().navigate(action)
            }
        })
    }

    private fun initView() {

        binding.rcSearch.apply {
            adapter = searchItemAdapter
            layoutAnimation = Animation.setAnimation(requireContext())
        }

        lifecycleScope.launch {
            viewModel.result.observe(viewLifecycleOwner, Observer {
                it?.let {
                    searchItemAdapter.submitData(lifecycle, it)
                }
            })
        }
    }

    private fun handleSearch() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchMovieChanged(it)
                }
                return true
            }
        })
    }
}