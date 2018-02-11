package com.gimenez.kent.alarmandstopwatch

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

class Alarm : AppCompatActivity() {



    lateinit var alarmManager: AlarmManager
    lateinit var timePicker: TimePicker
    lateinit var updateText: TextView
    lateinit var con: Context
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    var hour : Int = 0
    var min : Int = 0
    lateinit var PI : PendingIntent

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_alarm)


    this.con = this
    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    timePicker = findViewById(R.id.timePicker) as TimePicker
    updateText = findViewById(R.id.tvUpdate) as TextView
    btnStart = findViewById(R.id.btnStartAlarm) as Button
    btnStop = findViewById(R.id.btnEndAlarm) as Button
    var calendar: Calendar = Calendar.getInstance()
    var myIntent : Intent = Intent(this,AlarmReceiver::class.java)
    btnStart.setOnClickListener(object: View.OnClickListener{
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onClick(v: View?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                calendar.set(Calendar.MINUTE, timePicker.minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                hour = timePicker.hour
                min = timePicker.minute
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.currentHour)
                calendar.set(Calendar.MINUTE, timePicker.currentMinute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                hour = timePicker.currentHour
                hour = timePicker.currentMinute
            }

            var hour_String: String = hour.toString()
            var minute_String: String = min.toString()
            if (hour > 12) {
                hour_String = (hour - 12).toString()

            }
            if (min < 10) {
                minute_String = "0$min"
            }
            setAlarmText("Alarm set to: $hour_String : $minute_String")
            val putExtra = myIntent.putExtra("extra", "on")
            PI = PendingIntent.getBroadcast(this@Alarm,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,PI)


        }

    })

    btnStop.setOnClickListener(object : View.OnClickListener{
        override fun onClick(v: View?) {
            setAlarmText("Alarm off")
            PI = PendingIntent.getBroadcast(this@Alarm,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(PI)
            myIntent.putExtra("extra", "off")
            sendBroadcast(myIntent)


        }

    })

}

    private fun setAlarmText(s: String) {
        updateText.setText (s)
    }


}



