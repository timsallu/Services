package com.example.services;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "EGServices";

    public MyService() {
    }

    IBinder mybinder=new Mybinder();

    Handler handler=new Handler();

    CallBack1 callBack1;

    public class Mybinder extends Binder
    {
        public  MyService getMyService()
        {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mybinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("EGServices", "Service Started"+Thread.currentThread());

     new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 50; i++) {
                    Log.d(TAG, "EGServices: The number is: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d("EGServices", "Cleared from Recent");
    }

     void getdata()
    {
        Log.d("EGServices", "Bound Service"+Thread.currentThread());

        new Thread(new Runnable() {
            @Override
            public void run() {


                callBack1.updateActivdfity("callBack1 "+Thread.currentThread());

                handler.postDelayed(this, 1000);
            }
        }).start();

    }

    void intializeCallback(Activity activity)
    {
        callBack1=(CallBack1)activity;
    }
}
