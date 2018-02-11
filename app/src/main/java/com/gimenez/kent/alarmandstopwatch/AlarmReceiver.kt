package com.gimenez.kent.alarmandstopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Kent on 2/11/2018.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var getResult: String = intent!!.getStringExtra("extra")


        var serviceIntent : Intent = Intent (context, RingtoneService::class.java)
        serviceIntent.putExtra("extra", getResult)
        context!!.startService(serviceIntent)



    }
}