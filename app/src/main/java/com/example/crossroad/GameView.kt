package com.example.crossroad

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    // Paint object to define drawing characteristics
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var highScore = 0
    private var sharkPosition = 0 // Current position of the player's character
    private val spikes = ArrayList<HashMap<String, Any>>() // List to store spike objects


    var viewWidth = 0
    var viewHeight = 0

    // Initialization block
    init {
        myPaint = Paint()
    }

    // Reset game state
    fun resetGame() {
        score = 0
        speed = 1
        time = 0
        sharkPosition = 0 // Reset shark position
        spikes.clear() // Clear spikes
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight


        if (time % (800 + speed * 10) < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random() // Randomly select a lane for the spike
            map["startTime"] = time // Record the creation time
            spikes.add(map)
        }
        time += 10 + speed // Increment time


        val sharkWidth = viewWidth / 5
        val sharkHeight = sharkWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val myCarDrawable = resources.getDrawable(R.drawable.shark, null)
        myCarDrawable.setBounds(
            sharkPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - sharkHeight,
            sharkPosition * viewWidth / 3 + viewWidth / 15 + sharkWidth - 25,
            viewHeight - 2
        )
        myCarDrawable.draw(canvas)

        // Draw spikes and handle collisions and score
        myPaint!!.color = Color.GREEN
        for (i in spikes.indices) {
            try {
                val X = spikes[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                val Y = time - spikes[i]["startTime"] as Int
                val otherCarDrawable = resources.getDrawable(R.drawable.spike, null)
                otherCarDrawable.setBounds(
                    X,
                    Y - sharkHeight,
                    X + sharkWidth,
                    Y
                )
                otherCarDrawable.draw(canvas)
                if (spikes[i]["lane"] as Int == sharkPosition) {
                    if (Y > viewHeight - 2 - sharkHeight && Y < viewHeight - 2) {
                        if (score > highScore) {
                            highScore = score
                        }
                        gameTask.closeGame(score, highScore) // End the game on collision
                    }
                }
                if (Y > viewHeight + sharkHeight) {
                    spikes.removeAt(i) // Remove spikes that have passed the screen
                    score++ // Increment score
                    speed = 1 + Math.abs(score / 8) // Adjust speed based on score
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Display score and speed information on the screen
        myPaint!!.color = Color.BLACK
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        canvas.drawText("High Score : $highScore", 80f, 140f, myPaint!!)
        invalidate()
    }

    // Handle touch events to control player's character movement
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                val laneWidth = viewWidth / 3
                sharkPosition = (x1 / laneWidth).toInt().coerceIn(0, 2)
                invalidate() // Redraw the view
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }
}
