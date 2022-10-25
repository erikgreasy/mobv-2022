package com.example.semestralka

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralka.databinding.FragmentFormBinding

class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.button.setOnClickListener {
            val action = FormFragmentDirections.actionFormFragmentToDrinkFragment(
                name = binding.nameInput.text.toString(),
                barName = binding.barNameInput.text.toString(),
                barLat = binding.barLatInput.text.toString(),
                barLong = binding.barLongInput.text.toString()
            )

            this.findNavController().navigate(action)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}