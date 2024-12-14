package com.example.crossroad

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class OnboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboard)

        // Find the spike ImageView elements
        val spike1 = findViewById<ImageView>(R.id.Spike1)
        val spike2 = findViewById<ImageView>(R.id.Spike2)
        val spike3 = findViewById<ImageView>(R.id.Spike3)
        val spike4 = findViewById<ImageView>(R.id.Spike4)

        // Create ObjectAnimator for each spike to move them around the shark
        val animator1 = ObjectAnimator.ofFloat(spike1, "rotation", 0f, 360f)
        animator1.repeatCount = ObjectAnimator.INFINITE
        animator1.duration = 2000
        animator1.start()

        val animator2 = ObjectAnimator.ofFloat(spike2, "rotation", 0f, 360f)
        animator2.repeatCount = ObjectAnimator.INFINITE
        animator2.duration = 2000
        animator2.start()

        val animator3 = ObjectAnimator.ofFloat(spike3, "rotation", 0f, 360f)
        animator3.repeatCount = ObjectAnimator.INFINITE
        animator3.duration = 2000
        animator3.start()

        val animator4 = ObjectAnimator.ofFloat(spike4, "rotation", 0f, 360f)
        animator4.repeatCount = ObjectAnimator.INFINITE
        animator4.duration = 2000
        animator4.start()

        // Use Handler to navigate to the MainActivity after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000) // 5000 milliseconds = 5 seconds
    }
}


