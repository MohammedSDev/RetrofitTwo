package co.clickapps.retrofittwo.rxjava.http;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import co.clickapps.retrofittwo.MainActivity;
import co.clickapps.retrofittwo.rxjava.http.PostApiService;
import co.clickapps.retrofittwo.rxjava.model.PostModel;
import co.clickapps.retrofittwo.rxjava.model.UserModel;
import co.clickapps.retrofittwo.rxjava.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clickapps on 25/12/17.
 */

//public class HttpManager extends LiveData<PostModel> {
public class HttpManager extends LiveData<UserModel> {

    private final String TAG ="mud";
    public HttpManager() {


    }


    @Deprecated
    public void getPosts(){
        PostApiService service = MainActivity.Test.Build(PostApiService.class, "https://jsonplaceholder.typicode.com/");
        service.getPost().enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Log.d(TAG, "onResponse: ");
                ready(response.body());
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.d(TAG, "onFailure: .........................$#%");
            }
        });
    }


    public void getUser(){
        /*PostApiService service = MainActivity.Test.Build(PostApiService.class, "https://jsonplaceholder.typicode.com/");*//*Posts Base Url*/
        PostApiService service = MainActivity.Test.Build(PostApiService.class, "https://reqres.in/");
        service.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d(TAG, "onResponse: ");

                userReady(response.body().getData());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void userReady(UserModel data) {
        Log.d(TAG, "userReady: ");
        setValue(data);
    }


    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive: ");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive: ");
    }

    @Deprecated
    private void ready(PostModel model){
        Log.d(TAG, "ready: ");
//        setValue(model);
    }
}
