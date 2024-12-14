package com.example.crossroad

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    var gameRunning = false
    private lateinit var sharedPreferences: SharedPreferences

    // Define a key for the high score in SharedPreferences
    private val HIGH_SCORE_KEY = "high_score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this, this)

        // Display the initial high score
        displayHighScore()

        startBtn.setOnClickListener {
            if (!gameRunning) {
                gameRunning = true
                mGameView.resetGame() // Reset the game state
                mGameView.setBackgroundResource(R.drawable.nice)
                rootLayout.addView(mGameView)
                startBtn.visibility = View.GONE
                score.visibility = View.GONE
            }
        }
    }

    override fun closeGame(mScore: Int, highScore: Int) {
        score.text = "Score : $mScore | High Score: $highScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        gameRunning = false

        // Update and save the high score if necessary
        updateHighScore(mScore)
    }

    // Method to display the high score
    private fun displayHighScore() {
        val highScore = getHighScore()
        score.text = "High Score: $highScore"
    }

    // Method to save the high score in SharedPreferences
    private fun saveHighScore(score: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(HIGH_SCORE_KEY, score)
        editor.apply()
    }

    // Method to retrieve the high score from SharedPreferences
    private fun getHighScore(): Int {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0) // 0 is the default value if the key doesn't exist
    }

    // Method to update and display the high score if the new score is greater
    private fun updateHighScore(score: Int) {
        val currentHighScore = getHighScore()
        if (score > currentHighScore) {
            saveHighScore(score)
            displayHighScore()
        }
    }
}
