package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.semestralka.databinding.FragmentLoginBinding
import com.example.semestralka.databinding.FragmentNearBarsListBinding

class NearBarsList : Fragment() {
    private var _binding: FragmentNearBarsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearBarsListBinding.inflate(inflater, container, false)

        return binding.root
    }
}