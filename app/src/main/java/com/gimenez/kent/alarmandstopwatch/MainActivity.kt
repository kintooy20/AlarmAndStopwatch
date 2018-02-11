package com.gimenez.kent.alarmandstopwatch


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        img1.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)

        }

        img2.setOnClickListener {
            val intent = Intent(this, Stopwatch::class.java)
            startActivity(intent)

        }


    }
}









