package co.clickapps.retrofittwo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by clickapps on 21/12/17.
 */

public class MyIntentService extends IntentService {

    private String TAG ="mud";
    private MyBinder mBinder;
    private Intent sendBroadCast;

    public MyIntentService() {
        super("name");
        mBinder = new MyBinder();
        sendBroadCast = new Intent("sendBroadCast");
//        setIntentRedelivery(true);

        Log.d(TAG, "MyIntentService: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
//        return super.onBind(intent);
        
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        while (true){
            try {
                //wait
                Thread.sleep(5000);

                LocalBroadcastManager.getInstance(this).sendBroadcast(sendBroadCast);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.d(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


    //classes
    public class MyBinder extends Binder{
        public MyIntentService getService(){
            return MyIntentService.this;
        }
    }
}
