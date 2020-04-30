package com.example.services;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class WorkActivity extends AppCompatActivity {

    //ref : https://www.simplifiedcoding.net/android-workmanager-tutorial/
    public static final String MESSAGE_STATUS = "message_status";
    TextView tvStatus;
    Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_work);


        Log.d("EGServices", "WorkActivity onCreate ");

        tvStatus = findViewById(R.id.tvStatus);
        btnSend = findViewById(R.id.btnSend);

        final WorkManager mWorkManager = WorkManager.getInstance(WorkActivity.this);

        /*
Adding Constraints
Now let’s add some constraint in our work so that it will execute at a specific time. We have many constraints available for example.

setRequiresCharging(boolean b): If it is set true the work will be only done when the device is charging.
setRequiresBatteryNotLow(boolean b): Work will be done only when the battery of the device is not low.
setRequiresDeviceIdle(boolean b): Work will be done only when the device is idle.
* */
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)// you can add as many constraints as you want
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        final OneTimeWorkRequest workRequest =
                new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        .setInputData(new Data.Builder().putString(MESSAGE_STATUS,"Task data passed").build())
                        .setConstraints(constraints)
                        .build();

        final OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInputData(new Data.Builder().putString(MESSAGE_STATUS,"Task data passed").build())
                .build();


        //fro PeriodicWorkRequest  In the above code we are defining that the work should be done after every 10 hours.
        final PeriodicWorkRequest periodicWorkRequest
                = new PeriodicWorkRequest.Builder(NotificationWorker.class, 10, TimeUnit.HOURS)
                .build();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Salman", "btnSend");

                mWorkManager.cancelWorkById(mRequest.getId());

                mWorkManager.enqueue(workRequest);

                //Chaining Works
      /*  mWorkManager.
                beginWith(mRequest)
                .then(mRequest)
                .then(workRequest)
                .enqueue();*/
            }
        });

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {

                    Log.d("Salman", "onChanged");

                    WorkInfo.State state = workInfo.getState();

                    if(workInfo.getState().isFinished())
                        tvStatus.append(workInfo.getOutputData().getString(NotificationWorker.WORK_RESULT) + "\n");

                    tvStatus.append(workInfo.getState().name() + "\n");
                }
            }
        });





        //Cancel work

        /*We also have the following methods to cancel the work.
cancelAllWork(): To cancel all the work.
cancelAllWorkByTag(): To cancel all works of a specific tag.
cancelUniqueWork(): To cancel a unique work.
        *
        * */

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("EGServices", " WorkActivity onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("EGServices", " WorkActivity onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("EGServices", " WorkActivity onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("EGServices", " WorkActivity onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EGServices", " WorkActivity onDestroy ");
    }
}
