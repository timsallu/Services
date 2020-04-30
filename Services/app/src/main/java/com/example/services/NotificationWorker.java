package com.example.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker
{
    public static final String WORK_RESULT = "work_result";

    boolean checkdata=false;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        Log.d("Salman", "NotificationWorker");
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("Salman", "doWork");


        Data taskData = getInputData();
        String taskDataString = taskData.getString(WorkActivity.MESSAGE_STATUS);

        showNotification("WorkManager", taskDataString != null ? taskDataString : "Message has been Sent");
        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 150; i++) {
                    Log.d("Salman", "Salman: The number is: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                checkdata =true;

            }
        }).start();

        do {
            if(checkdata) {
                Log.d("Salman", "doWork S" + Result.success(outputData));
                return Result.success(outputData);
            }
            }
        while (!checkdata) ;

        Log.d("Salman", "doWork F" + Result.success(outputData));

        return Result.failure(outputData);
    }

    private void showNotification(String task, String desc) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }

}
