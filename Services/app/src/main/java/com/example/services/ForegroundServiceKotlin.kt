package com.example.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundServiceKotlin : Service()
{

    private val CHANNEL_ID = "ForegroundService Kotlin"

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("EGServicesAsync","ForegroundServiceKotlin start");

        val input = intent?.getStringExtra("inputExtra")
        Log.d("EGServicesAsync","ForegroundServiceKotlin start"+input);
        print(input)

        createNotificationChannel();
        val intent = Intent(this, MainActivity::class.java)
        val pendingintent = PendingIntent.getActivity(this,0,intent,0)
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("vhsduihvsd")
                .setContentText("vgiubfvdsoucidvcdsui")
                .setAutoCancel(true)
                .setContentIntent(pendingintent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()
        startForeground(1,notification)



        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}