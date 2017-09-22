package co.clickapps.retrofittwo;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by clickapps on 17/9/17.
 */

public interface CallBack {

    <T>void onSuccess(T model, Response<T> response,int taskID,String message);
    <T>void onfailed(ErrorModel model, String retrofitError,String message);
}
