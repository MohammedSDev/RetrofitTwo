package co.clickapps.retrofittwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.UiThread;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import co.clickapps.retrofittwo.glide.GlideApp;
import co.clickapps.retrofittwo.realm.model.*;
import co.clickapps.retrofittwo.realm.model.UserModel;
import co.clickapps.retrofittwo.service.MyServiceHelper;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "mud";
    ImageView imageView;
    Button button;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_main_activity);
        button =  findViewById(R.id.my_button);
        button.setOnClickListener(this);
        //init Realm.this should call only once,, and good place is in Application class.
        Realm.init(this.getApplicationContext());



//        glideTest();



    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, intent.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("hhhhhhhhh.co");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    private void glideTest() {
        final String[] eatFoodyImages = {
                "http://i.imgur.com/rFLNqWI.jpg",
                "http://i.imgur.com/C9pBVt7.jpg",
                "http://i.imgur.com/rT5vXE1.jpg",
                "http://i.imgur.com/aIy5R2k.jpg",
                "http://i.imgur.com/MoJs9pT.jpg",
                "http://i.imgur.com/S963yEM.jpg",
                "http://i.imgur.com/rLR2cyc.jpg",
                "http://i.imgur.com/SEPdUIx.jpg",
                "http://i.imgur.com/aC9OjaM.jpg",
                "http://i.imgur.com/76Jfv9b.jpg",
                "http://i.imgur.com/fUX7EIB.jpg",
                "http://i.imgur.com/syELajx.jpg",
                "http://i.imgur.com/COzBnru.jpg",
                "http://i.imgur.com/Z3QjilA.jpg",
        };

//        GlideApp.with(this)
//                .load(eatFoodyImages[0])
//                .transition(DrawableTransitionOptions.withCrossFade(1000))
//                .into(imageView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final String item :
                        eatFoodyImages) {
                    try {
                        final Bitmap bitmap = GlideApp.with(MainActivity.this)
                                .asBitmap()
                                .load(item)
                                .centerCrop()
                                .submit(500, 500)
                                .get();
//                        imageView.setImageBitmap(bitmap);

                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                Log.d(TAG, "run: download: " + item);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();

    }


    //    Realm: get users and print some details
    private void getUsers() {
        Realm realm = Realm.getDefaultInstance();
        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: start get data");
        RealmQuery<UserModel> all = realm.where(UserModel.class);

        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: count:" + all.findAll().size());
        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: lastUser:" + all.findAll().last());
        
    }

    private void insertDataIntoRealmDataBase(int count) {

        co.clickapps.retrofittwo.realm.model.UserModel user;
        PhoneModel phone;
        phone = new PhoneModel();
        user = new UserModel();

        UserModel userRealm = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < count; i++) {

            user.setId(i + "");
            user.setName("user number " + i);
            //set phones
            phone.setId(i + "");
            phone.setNumber("1234567" + i);
            RealmList<PhoneModel> phoneModels = new RealmList<>();
            phoneModels.add(phone);
            user.setPhones(phoneModels);


//            userRealm = realm.copyToRealm(user);
            userRealm = realm.copyToRealmOrUpdate(user);

        }
        realm.commitTransaction();
        Log.d(TAG, "insertDataIntoRealmDataBase: compeletd");
        Log.d(TAG, "insertDataIntoRealmDataBase: last user " + userRealm.getId());



        getUsers();


    }

    private void insertDataIntoRealmDataBaseAsync(final int count) {

        final co.clickapps.retrofittwo.realm.model.UserModel user;
        final PhoneModel phone;
        phone = new PhoneModel();
        user = new UserModel();


        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                new Realm.Transaction() {
                      @Override
                      public void execute(Realm realm) {
                          //// TODO: 17/9/17 check if all rows are inserted or only last row?
                          UserModel userRealm = null;
                          for (int i = 0; i < count; i++) {

                              user.setId(i + "");
                              user.setName("user number " + i);
                              //set phones
                              phone.setId(i + "");
                              phone.setNumber("1234567" + i);
                              RealmList<PhoneModel> phoneModels = new RealmList<>();
                              phoneModels.add(phone);
                              user.setPhones(phoneModels);


//                              userRealm = realm.copyToRealm(user);
                              userRealm = realm.copyToRealmOrUpdate(user);

                          }
                      }
                }, new Realm.Transaction.OnSuccess() {
                      @Override
                      public void onSuccess() {
                          Log.d(TAG, "insertDataIntoRealmDataBase:onSuccess compeletd");
                      }
                }
                , new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "insertDataIntoRealmDataBase:onError " + error.getMessage());
                    }
                });


//        Log.d(TAG, "insertDataIntoRealmDataBase: last user " + userRealm.getId());

        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: start get data");
        RealmResults<UserModel> all = realm.where(UserModel.class)
                .findAll();
        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: count:" + all.size());
        Log.d(TAG, "insertDataIntoRealmDataBaseAsync: lastUser:" + all.last());

    }

    private void tryRetrofitTow() {
        RetrofitInterface build = Test.Build(RetrofitInterface.class, "http://www.imdb.com/list/ls033652811/");

        Test.te(build.getUser(), 1, new CallBack() {
            @Override
            public <T> void onSuccess(T model, Response<T> response, int taskID, String message) {
                Log.d(TAG, "onSuccess: ok" + taskID);
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
                Log.d(TAG, "onSuccess: ok" + taskID);
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

    public void onCLick(View view) {
        MyServiceHelper.getInstance(this).startMyService();
    }

    @Override
    public void onClick(View view) {
        onCLick(view);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            receiver = null;
        }

    }
}
