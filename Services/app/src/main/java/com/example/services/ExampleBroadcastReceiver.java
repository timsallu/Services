package com.example.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ExampleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.codinginflow.EXAMPLE_ACTION".equals(intent.getAction())) {
            String receivedText = intent.getStringExtra("com.codinginflow.EXTRA_TEXT");
            Toast.makeText(context, receivedText, Toast.LENGTH_SHORT).show();

            Log.d("CounterService", "onReceive Job Execution Starting.....ACTION_taskremovedBR........");
            Intent mIntent = new Intent(context, MyIntentService.class);
            mIntent.putExtra("maxCountValue", 1000);
            context.startService(mIntent);
        }
    }
}