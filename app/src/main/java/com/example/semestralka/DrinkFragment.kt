package com.example.semestralka

import android.content.Intent
import android.net.Uri
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
    private lateinit var nameId: String;
    private lateinit var barNameId: String;
    private lateinit var barLat: String;
    private lateinit var barLong: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nameId = it.getString("name").toString()
            barNameId = it.getString("barName").toString()
            barLat = it.getString("barLat").toString()
            barLong = it.getString("barLong").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.name.text = nameId
        binding.barName.text = barNameId

        val animation: LottieAnimationView = binding.animationView

        animation.setOnClickListener {
            launchAnimation(animation)
        }

        binding.showOnMapBtn.setOnClickListener {
            val queryURl: Uri = Uri.parse("geo:${barLat},${barLong}")
            val intent = Intent(Intent.ACTION_VIEW, queryURl)
            startActivity(intent)
        }

        return view
    }

    private fun launchAnimation(animationView: LottieAnimationView) {
        animationView.loop(false)
        animationView.playAnimation()
    }
}