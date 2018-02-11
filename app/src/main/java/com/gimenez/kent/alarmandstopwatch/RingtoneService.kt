package com.gimenez.kent.alarmandstopwatch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.support.v7.app.NotificationCompat
import com.gimenez.kent.alarmandstopwatch.RingtoneService.Companion.ringtone

/**
 * Created by Kent on 2/11/2018.
 */
class RingtoneService : Service () {

    companion object {
        lateinit var ringtone: Ringtone
    }

        var id : Int = 0
        var isRunning :Boolean = false


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            var state: String= intent!!.getStringExtra("extra")
        assert(state!=null)
        when (state){

            "on" -> id = 1
            "off" -> id = 0
        }

        if (!this.isRunning && id ==1){
            playAlarm()
            this.isRunning = true
            this.id = 0
            firenotification()
        }
        else if (this.isRunning && id ==0){
            ringtone.stop()
            this.isRunning = false
            this.id = 0
        }
        else if (this.isRunning && id == 0){

            this.isRunning = false
            this.id = 0
        }
        else if (this.isRunning && id == 1){
            this.isRunning = true
            this.id = 1
        }
        else {

        }

        return START_NOT_STICKY

    }
   private fun firenotification() {
        var main_activity_intent : Intent  = Intent(this, MainActivity::class.java)
        var pi : PendingIntent = PendingIntent.getActivity(this,0,main_activity_intent,0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notify_manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notification: Notification? = NotificationCompat.Builder (this)
                .setContentTitle("Alarm is OFF")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentText("Click Me")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()

            notify_manager.notify(0,notification)
    }

    private fun playAlarm() {
        var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        ringtone = RingtoneManager.getRingtone(baseContext, alarmUri)
        ringtone.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }
}