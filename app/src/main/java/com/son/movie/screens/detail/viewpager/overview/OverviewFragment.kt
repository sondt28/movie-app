package com.son.movie.screens.detail.viewpager.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.son.movie.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var overviewArgs: String
    @Inject lateinit var viewModelFactory: OverviewViewModel.AssistedFactory

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater)
        overviewArgs = arguments?.getString("overview").toString()

        viewModel =
            ViewModelProvider(this, OverviewViewModel.provideFactory(viewModelFactory, overviewArgs))[OverviewViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}