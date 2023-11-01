package com.son.movie.screens.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.son.movie.R
import com.son.movie.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private var dialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater)
        handleOnClickListener()
        return binding.root
    }

    private fun handleOnClickListener() {
        binding.btnLogin.setOnClickListener {
            handleLoginDialog()
        }
    }

    private fun handleLoginDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.ThemeDiaLogCustom)
        val view = layoutInflater.inflate(R.layout.custom_login_dialog, null)
        alertDialogBuilder.setView(view)

        val btnClose = view.findViewById<ImageView>(R.id.ivClose)
        val btnLogin = view.findViewById<AppCompatButton>(R.id.btnLogin)
        val cardView = view.findViewById<CardView>(R.id.cardView)

        dialog = alertDialogBuilder.create()
        dialog?.show()

        btnClose.setOnClickListener {
            dialog?.dismiss()
        }

        btnLogin.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToHomeFragment()
            findNavController().navigate(action)
            dialog?.dismiss()
        }
    }
}