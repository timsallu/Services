package com.example.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class taskremovedBR extends BroadcastReceiver {
    public static final String ACTION_taskremovedBR = "android.intent.action.BOOT_COMPLETEDdd";
    private static final String TAG = "CounterService";

    @Override
    public void onReceive(Context mContext, Intent intent) {

        Log.d(TAG, "onReceive Job Execution Starting.............");

        if (intent.getAction().equals(ACTION_taskremovedBR)) {
            Log.d(TAG, "onReceive Job Execution Starting.....ACTION_taskremovedBR........");
            Intent mIntent = new Intent(mContext, MyIntentService.class);
            mIntent.putExtra("maxCountValue", 100);
            mContext.startService(mIntent);
        }
    }
}