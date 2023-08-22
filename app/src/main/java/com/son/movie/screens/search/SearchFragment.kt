package com.son.movie.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.son.movie.R
import com.son.movie.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        handleSearch()

        binding.rcSearch.adapter = SearchItemAdapter(SearchItemAdapter.OnClickListener {
            viewModel.displayDetailsFilm(it.id)
        })

        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
                findNavController().navigate(action)
                viewModel.displayDetailsFilmComplete()
            }
        })

        return binding.root
    }

    private fun handleSearch() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getSearchResult(it) }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchResult(it) }
                return true
            }
        })
    }
}