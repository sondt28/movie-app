package com.son.movie.screens.detail.viewpager.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.son.movie.R
import com.son.movie.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var overview: String
    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater)
        arguments?.let {
            overview = it.getString("overview").toString()
        }

        val application = requireNotNull(activity).application
        val viewModelFactory = OverviewViewModelFactory(overview, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[OverviewViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}