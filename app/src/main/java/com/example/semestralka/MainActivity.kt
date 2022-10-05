package com.example.semestralka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation: LottieAnimationView = findViewById(R.id.animationView)

        animation.setOnClickListener {
            launchAnimation(animation)
        }
    }

    private fun launchAnimation(animationView: LottieAnimationView) {
        animationView.loop(false)
        animationView.playAnimation()
    }
}