package com.example.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

  //      val intent = Intent(this, ForegroundServiceKotlin::class.java)
   //     ContextCompat.startForegroundService(this,intent);

        val filter = IntentFilter()
        filter.addAction("broadcast")
        registerReceiver(bootCompleteReceiver,filter)

    }

    val bootCompleteReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(context, "hi", Toast.LENGTH_LONG).show()
            Log.d("EGServicesAsync","vndsovbsid");
        }
    }





}
