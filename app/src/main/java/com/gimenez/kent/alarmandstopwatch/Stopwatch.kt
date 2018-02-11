package com.gimenez.kent.alarmandstopwatch

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import kotlinx.android.synthetic.main.activity_stopwatch.*

class Stopwatch : AppCompatActivity() {



    internal var customHandler = Handler()
    internal var startTime = 0L
    internal var timeInMilleseconds = 0L
    internal var timeSwapBuff = 0L
    internal var updateTime = 0L


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)




        var updateTimeThread: Runnable = object :Runnable{
            override fun run() {
                timeInMilleseconds = SystemClock.uptimeMillis() - startTime
                updateTime = timeSwapBuff + timeInMilleseconds
                var seconds = (updateTime/1000).toInt()
                val minutes = seconds/60
                val milliseconds = (updateTime % 1000).toInt()

                txtTimer.text = ("" + minutes + ":" + String.format("%02d", seconds)
                        + ":" + String.format("%03d", milliseconds))
                customHandler.postDelayed(this,0)
            }


        }


        btnStart.setOnClickListener{
                    startTime = SystemClock.uptimeMillis()
                    customHandler.postDelayed(updateTimeThread,0)
                    btnStart.visibility = View.INVISIBLE
                    btnPause.visibility = View.VISIBLE

                }

        btnPause.setOnClickListener {
            timeSwapBuff += timeInMilleseconds
            customHandler.removeCallbacks(updateTimeThread)
            btnStart.visibility = View.VISIBLE
            btnStart.setText("Resume")

        }



        btnReset.setOnClickListener{
                txtTimer.text = "0:00:00"
                customHandler = Handler()
                customHandler.removeCallbacks(updateTimeThread)

            btnStart.visibility = View.VISIBLE
            btnStart.setText("Start")
            btnPause.visibility = View.INVISIBLE

        }

    }


    }


