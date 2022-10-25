package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.example.semestralka.databinding.FragmentDrinkBinding
import com.example.semestralka.databinding.FragmentFormBinding

class DrinkFragment : Fragment() {
    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        val view = binding.root

        val animation: LottieAnimationView = binding.animationView
        animation.setOnClickListener {
            launchAnimation(animation)
        }

        return view
    }

    private fun launchAnimation(animationView: LottieAnimationView) {
        animationView.loop(false)
        animationView.playAnimation()
    }
}