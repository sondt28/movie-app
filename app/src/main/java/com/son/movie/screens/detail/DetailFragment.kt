package com.son.movie.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.son.movie.R
import com.son.movie.databinding.FragmentDetailBinding
import com.son.movie.screens.detail.viewpager.DemoMovieDetailAdapter

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var demoMovieDetailAdapter: DemoMovieDetailAdapter
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)

        val args = DetailFragmentArgs.fromBundle(requireArguments()).movieId

        val application = requireNotNull(activity).application
        val viewModelFactory = DetailViewModelFactory(args, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupTabLayout()
        setupListener()

        return binding.root
    }

    private fun setupListener() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTabLayout() {
        viewModel.selectMovie.observe(viewLifecycleOwner, Observer {
            demoMovieDetailAdapter = DemoMovieDetailAdapter(this, it.overview)
            binding.pager.adapter = demoMovieDetailAdapter

            TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.about_movie)
                    1 -> tab.text = getString(R.string.reviews)
                    2 -> tab.text = getString(R.string.cast)
                }

            }.attach()
        })
    }
}