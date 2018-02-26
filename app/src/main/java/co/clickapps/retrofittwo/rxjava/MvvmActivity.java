package co.clickapps.retrofittwo.rxjava;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import co.clickapps.retrofittwo.R;
import co.clickapps.retrofittwo.rxjava.http.HttpManager;
import co.clickapps.retrofittwo.rxjava.model.PostModel;
import co.clickapps.retrofittwo.rxjava.model.UserModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MvvmActivity extends AppCompatActivity implements Observer<UserModel> {

    private final String TAG ="mud";
    TextView textView;
//    HttpManager http;
    Observable<UserModel> observableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        textView = findViewById(R.id.mvvm_textview);


        /*connect to HttpManager*/
//        http = new HttpManager();
//                http.observe(this, new Observer<UserModel>() {
//            @Override
//            public void onChanged(@Nullable UserModel userModel) {
//                setData(userModel);
//            }
//        });


    }


    @Deprecated
    /*every click create new Observe and this make LiveData call setValue.so onChange callback fire every time*/
    public void mvvmClick1(View view){
        Log.d(TAG, "mvvmClick: ");
//        http.getUser();
        ViewModelProviders.of(this)
                .get(MvvmViewModel.class)
                .getUser()
                .observe(this, new Observer<UserModel>() {
                    @Override
                    public void onChanged(@Nullable UserModel userModel) {
                        setData(userModel);
                    }
                });

    }
    /*this will use this activity as Observer,it will not craete new instance each time.let's chek if ir will fire onChange callback by every click/call to this methods*/
    /*Result: no Call every time,just one time*/
    public void mvvmClick(View view){
        Log.d(TAG, "mvvmClick: ");
//        http.getUser();
        ViewModelProviders.of(this)
                .get(MvvmViewModel.class)
                .getUser()
                .observe(this, this);

    }

    /*Use RxJava/Observale methods/way*/
    public void mvvmClick3(View view){
        Log.d(TAG, "mvvmClick: ");
//        http.getUser();

        /*Observable part.create Observale of specific request*/
        observableUser = ViewModelProviders.of(this)
                .get(MvvmViewModel.class)
                .getObservableUser(getLifecycle());

        /*Schedulers part*/
       /* observableUser =  observableUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
        /*Subscribe part*/
        observableUser.subscribe(new io.reactivex.Observer<UserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull UserModel userModel) {
                        Log.d(TAG, "onNext: ");
                        setData(userModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        /*observableUser.subscribe(new io.reactivex.Observer<UserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull UserModel userModel) {
                        Log.d(TAG, "onNext2: ");
                        setData(userModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });*/

    }

    private void setData(UserModel userModel) {
        Log.d(TAG, "setData: ");
        textView.append(userModel.getId());
        textView.append("\n getFirst name:");
        textView.append(userModel.getFirst_name());
        textView.append("\ngetLast name: ");
        textView.append(userModel.getLast_name());
        String string = getResources().getString(R.string.app_name);
        Snackbar.make(findViewById(android.R.id.content),"SnakBar...",Snackbar.LENGTH_LONG).show();
//        Toast.makeText(this, "Data came..", Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    private void setData(PostModel postModel) {
        textView.setText(postModel.getId());
        textView.append("\n Title:");
        textView.append(postModel.getTitle());
        textView.append("\nBody: ");
        textView.append(postModel.getBody());
    }

    @Override
    public void onChanged(@Nullable UserModel userModel) {
        Log.d(TAG, "onChanged: ");
        setData(userModel);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (observableUser != null){

        }
    }
}
