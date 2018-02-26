package co.clickapps.retrofittwo.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import co.clickapps.retrofittwo.R;

public class ActivityOne extends AppCompatActivity implements ServiceConnection {


    private String TAG ="mud";
    private Intent intent;
    private IntentFilter filter;
    private BroadcastReceiver reciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);


        reciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: ActivityTwo");
            }
        };
        filter = new IntentFilter("sendBroadCast");
        intent = new Intent(this,MyIntentService.class);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ActivityOne");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ActivityOne");
//        bindService(intent, this ,BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(reciver,filter);
        startService(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ActivityOne");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ActivityOne");

//        unbindService(this);
        stopService(intent);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reciver);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d(TAG, "onServiceConnected: ActivityOne");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected: ActivityOne");
    }
}
