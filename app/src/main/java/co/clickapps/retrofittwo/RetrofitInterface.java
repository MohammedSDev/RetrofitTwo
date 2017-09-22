package co.clickapps.retrofittwo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by clickapps on 17/9/17.
 */

public interface RetrofitInterface {


    @GET("api/users/2")
//    @GET("https://www.journaldev.com/13639/retrofit-android-example-tutorial")
    Call<UserModel> getUser();


    @Headers(MyConstant.AUTH_key + ": false")
    @GET("/api/users/2?delay=7")
//    @GET("https://www.journaldev.com/13639/retrofit-android-example-tutorial")
    Call<UserModel> getUsers();
}
