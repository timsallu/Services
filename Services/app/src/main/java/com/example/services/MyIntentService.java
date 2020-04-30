package com.example.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService {
    final Handler mHandler = new Handler();
    private static final String TAG = "CounterService";
    ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private PowerManager.WakeLock wakeLock;

    /**
     * provide name of worker thread
     */
    public MyIntentService() {
        super(TAG);
        setIntentRedelivery(true);//
    }
    @Override
    public void onCreate() {
        super.onCreate();
        showToast("Job Execution Started");
        Log.d(TAG, "Job Execution Started");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ExampleApp:Wakelock");
        wakeLock.acquire();
        Log.d(TAG, "Wakelock acquired");

        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build();

            startForeground(1, notification);
        }

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int maxCount = intent.getIntExtra("maxCountValue", -1);
        /**
         * Suppose we are performing task 1 to 1000, Each task will takes time 1 sec , So You saw we sleep thread or one second.
         */
        Log.d(TAG, "Job onHandleIntent Started");

        for (int i = 0; i < maxCount; i++) {
            Log.d(TAG, "onHandleWork: The number is: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // Helper for showing tests
    void showToast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Job Execution Started"+text);
                Toast.makeText(MyIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d(TAG, "onTaskRemoved");
        IntentFilter filter1 = new IntentFilter("com.codinginflow.EXAMPLE_ACTION");
        registerReceiver(exampleBroadcastReceiver, filter1);

        Intent intent = new Intent("com.codinginflow.EXAMPLE_ACTION");
        intent.putExtra("com.codinginflow.EXTRA_TEXT", "Broadcast received");
        sendBroadcast(intent);

        Log.d(TAG, "onTaskRemoved1");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        wakeLock.release();
        Log.d(TAG, "Wakelock released");
    }
}