package co.clickapps.retrofittwo.rxjava.http;

import java.util.List;

import co.clickapps.retrofittwo.MyConstant;
import co.clickapps.retrofittwo.rxjava.model.PostModel;
import co.clickapps.retrofittwo.rxjava.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by clickapps on 25/12/17.
 */

public interface PostApiService {

    /*https://jsonplaceholder.typicode.com/posts*/
    @Headers(MyConstant.AUTH_key + ": false")
    @GET("posts")
    Call<List<PostModel>> getPostsList();


    @Headers(MyConstant.AUTH_key + ": false")
    @GET("posts/1")
    Call<PostModel> getPost();

    @Headers(MyConstant.AUTH_key + ": false")
    @GET("api/users/2?delay=7")
    Call<UserResponse> getUser();
}
