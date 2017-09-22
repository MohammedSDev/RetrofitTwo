package co.clickapps.retrofittwo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import co.clickapps.retrofittwo.CallBack;
import co.clickapps.retrofittwo.Client;
import co.clickapps.retrofittwo.ErrorModel;
import co.clickapps.retrofittwo.MainActivity;
import co.clickapps.retrofittwo.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clickapps on 22/9/17.
 */

public class MyService extends IntentService {

    private static final String TAG = "mud";
    private IBinder iBinder;


    @Override
    public void onCreate() {
        super.onCreate();
        iBinder = new CustomService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return super.onBind(intent);
    }

    public MyService() {
        super("frayS");
        Log.d(TAG, "MyService: ");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {

        tryRetrofitTow();

    }

    private void tryRetrofitTow() {
        RetrofitInterface build = Test.Build(RetrofitInterface.class, "http://www.imdb.com/list/ls033652811/");

        Test.te(build.getUser(), 1, new CallBack() {
            @Override
            public <T> void onSuccess(T model, Response<T> response, int taskID, String message) {
                Log.d(TAG, "onSuccess: no delay ok" + taskID);

                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "onSuccess: no delay " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public <T> void onfailed(ErrorModel model, String retrofitError, String message) {
                Log.d(TAG, "onfailed: failed");
            }
        });

        build = Test.Build(RetrofitInterface.class);
        Test.te(build.getUsers(), 2, new CallBack() {
            @Override
            public <T> void onSuccess(T model, Response<T> response, int taskID, String message) {
                Log.d(TAG, "onSuccess: with delay ok" + taskID);


                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "onSuccess:with delay " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public <T> void onfailed(ErrorModel model, String retrofitError, String message) {
                Log.d(TAG, "onfailed: failed");
            }
        });


//        RetrofitInterface build = Client.build(RetrofitInterface.class);
//        build.getUser().enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                Log.d(TAG, "onResponse: ok");
//
//            }
//
//            @Override
//            public void onFailure(Call<UserModel> call, Throwable t) {
//                Log.d(TAG, "onFailure: failed");
//            }
//        });
    }

    private void withThread(@Nullable final Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = intent.getIntExtra("count",0);;
                for (int i = 0; i < count ; i++) {
                    try {

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onHandleIntent: count: "+ i);
                }
            }
        }).start();
    }

    private void noThread(@Nullable Intent intent) {
        try {
            int count = intent.getIntExtra("count",0);
            for (int i = 0; i < count ; i++) {
                Thread.sleep(1000);
                Log.d(TAG, "onHandleIntent: count: "+ i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("mud", "onHandleIntent: Complete");
        Intent intent1 = new Intent("hhhhhhhhh.co");
        intent1.putExtra("result","Hayyy.. completed at: " + System.currentTimeMillis());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: ");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    //class
    public class CustomService extends Binder{
        public MyService get(){
            return MyService.this;
        }
    }


    //this is the Controller,but I created it here.
    static class Test {

        private static String TAG = "test";

        public static <S> S Build(Class<S> s) {
            S build = Client.build(s);
            return build;
        }

        public static <S> S Build(Class<S> s, String baseUrl) {

            S build = Client.build(s, baseUrl, 7);
            return build;
        }

        public static <T> void te(Call<T> call, final int taskID, final CallBack callBack) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful())
                        callBack.onSuccess(response.body(), response, taskID, response.message());
                    else
                        try {
                            Log.d(TAG, "onResponse: not success");
                            callBack.onfailed(null, null, response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: IOException");
                            callBack.onfailed(null, null, e.getMessage());
                        }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    Log.d(TAG, " RetrofitonFailure: " + t.getMessage());
                    callBack.onfailed(null, t.getMessage(), "Error");
                }
            });
        }
    }
}
