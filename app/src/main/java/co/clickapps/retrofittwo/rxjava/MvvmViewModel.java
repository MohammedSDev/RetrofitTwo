package co.clickapps.retrofittwo.rxjava;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import co.clickapps.retrofittwo.MainActivity;
import co.clickapps.retrofittwo.rxjava.http.HttpManager;
import co.clickapps.retrofittwo.rxjava.http.PostApiService;
import co.clickapps.retrofittwo.rxjava.model.UserModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by clickapps on 25/12/17.
 */

public class MvvmViewModel extends ViewModel implements LifecycleObserver{


    private final String TAG = "mud";

    HttpManager http;
    Observable<UserModel> observable;


    public MvvmViewModel() {
        Log.d(TAG, "MvvmViewModel: ");
    }


    private void initHttp() {
        /*connect to HttpManager*/
        http = new HttpManager();
    }

    /*in first call, http liveData call getUser api snd save response in some ware,
    * then in next call ,it just update the new observe with same data */
    public LiveData<UserModel> getUser() {
        if (http == null) {
            Log.d(TAG, "getUser: Http = null");
            initHttp();
            http.getUser();
        }
        return http;
    }

    public Observable<UserModel> getObservableUser(Lifecycle lc) {

        lc.addObserver(this);
        if (observable == null) {
            Log.d(TAG, "getObservableUser: null");
            observable = Observable.just("")
                    .map(new Function<String, UserModel>() {
                        @Override
                        public UserModel apply(@NonNull String s) throws Exception {
                            Log.d(TAG, "apply: ");
                            PostApiService service = MainActivity.Test.Build(PostApiService.class, "https://reqres.in/");
                            return service.getUser().execute().body().getData();
                        }
                    });

            observable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return observable;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)

    public void localOnStart(){
        Log.d(TAG, "localOnStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void localOn(){
        Log.d(TAG, "localOnStop: ");
    }

}
