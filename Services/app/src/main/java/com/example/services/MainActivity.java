package com.example.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements CallBack1 {

    Intent i;
    taskremovedBR taskremovedBR=new taskremovedBR();
    ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  i =new Intent(MainActivity.this,MyService.class);
     //   startService(i);

        Log.d("EGServices", " MainActivity onCreate ");

        IntentFilter filter = new IntentFilter("android.intent.action.BOOT_COMPLETEDdd");
        registerReceiver(taskremovedBR, filter);

        IntentFilter filter1 = new IntentFilter("com.codinginflow.EXAMPLE_ACTION");
        registerReceiver(exampleBroadcastReceiver, filter1);


        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 1000; i++) {
                    Log.d("EGServices", "EGServices: The number is: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Log.d("EGServicesAsync","Onclick load");
        AsyncTaskback asyncTaskback=new AsyncTaskback(MainActivity.this);
        asyncTaskback.execute("");
        int d=addition(2,5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("EGServices", " MainActivity onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("EGServices", " MainActivity onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EGServices", " MainActivity onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EGServices", " MainActivity onStop ");
    }



    private  static class AsyncTaskback extends AsyncTask<String,String,String>
    {
        private WeakReference<MainActivity> activityWeakReference;
        Handler handler=new Handler();

        public AsyncTaskback(MainActivity activity) {
            activityWeakReference=new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            Log.d("EGServicesAsync","onPreExecute load");
            super.onPreExecute();

            MainActivity activity=activityWeakReference.get();

            activity.progressBar = activity.findViewById(R.id.progress_bar);
            activity. progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            for (int i = 0; i < 10; i++) {
                Log.d("EGServicesAsync", "EGServices: The number is: " + i);

                final MainActivity activity=activityWeakReference.get();


                final int finalI = i;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Tak finished"+ finalI,Toast.LENGTH_SHORT).show();
                        activity. progressBar.setProgress(finalI*10);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
           activity. progressBar.setProgress(100);
           activity. progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(activity, "Tak finished",Toast.LENGTH_LONG).show();
        }
    }

    public void onclick(View view) {

        Log.d("EGServices","Onclick");

     //   bindService(i,serviceConnection,BIND_AUTO_CREATE);

        Intent i =new Intent(MainActivity.this,Main3Activity.class);
        startActivity(i);
        finish();
    }

    public ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyService service1;
            MyService.Mybinder myService= (MyService.Mybinder) service;
            service1=myService.getMyService();
            service1.intializeCallback(MainActivity.this);
            service1.getdata();

            Log.d("EGServices","onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.d("EGServices","onServiceDisconnected");


        }
    };

    public void onclick1(View view) {

        unbindService(serviceConnection);
        stopService(i);

    }



    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateActivdfity(String msg) {

        Log.d("EGServices","updateActivdfity"+msg);

        return null;
    }

    public void onclick2(View view) {

        Log.d("EGServices","updateActivdfity");
        Intent i =new Intent(this,MyIntentService.class);
        i.putExtra("maxCountValue",100);
        ContextCompat.startForegroundService(this,i);

   /*     Intent intent = new Intent("android.intent.action.BOOT_COMPLETEDdd");
        sendBroadcast(intent);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("EGServices", " Masin onDestroy ");

        unregisterReceiver(taskremovedBR);
        unregisterReceiver(exampleBroadcastReceiver);
        stopService();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    public void onclick3(View view) {
        startService();
    }

    public void JobIntentService(View view) {

        Intent mIntent = new Intent(this, MyJobIntentService.class);
        mIntent.putExtra("maxCountValue", 1000);
        MyJobIntentService.enqueueWork(this, mIntent);

    }

    public void WorkerClick(View view) {

        Intent workintent = new Intent(this, WorkActivity.class);
        startActivity(workintent);
      //  finish();
    }

    public int addition(int a , int b)
    {
        return a+b;
    }
}
