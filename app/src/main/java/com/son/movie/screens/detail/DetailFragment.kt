package com.son.movie.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.son.movie.R
import com.son.movie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)

        val args = DetailFragmentArgs.fromBundle(requireArguments()).movieId

        val application = requireNotNull(activity).application
        val viewModelFactory = DetailViewModelFactory(args, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}