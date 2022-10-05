package com.example.semestralka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.semestralka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation: LottieAnimationView = binding.animationView
        animation.setOnClickListener {
            launchAnimation(animation)
        }
    }

    private fun launchAnimation(animationView: LottieAnimationView) {
        animationView.loop(false)
        animationView.playAnimation()
    }
}