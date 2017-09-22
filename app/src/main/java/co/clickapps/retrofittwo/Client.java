package co.clickapps.retrofittwo;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by clickapps on 17/9/17.
 */

public class Client {

    private static Retrofit retrofit ;
    private static String mBaseUrl = "https://reqres.in/";
    //OkHttp.Builder
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    //Retrofit.Builder
    private static int tempBuilderCode;/*use this to check if last baseUrl used is same with what will use now,
    *so no need to create new RetrofitBuilder,
    * but checks tring is to long,so I have to check by int, and each baseUrl must have its own BaseUrlCode
    * */
    private static Retrofit.Builder tempBuilder;//this for any different baseUrl,using with change BaseUrl method
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create());
    //logging Interceptor
    private static HttpLoggingInterceptor log = new HttpLoggingInterceptor().
            setLevel(HttpLoggingInterceptor.Level.BODY);
    //add AUTH interpreter
    private static Interceptor fray = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            if (original.header(MyConstant.AUTH_key) == null){
                Log.d("mud", "intercept: create new Fray");
                Request newRequest = original.newBuilder()
                        .header(MyConstant.AUTH_key, MyConstant.AUTH)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(newRequest);
            }
            else
                return chain.proceed(original);
        }
    };

    /*Change BaseUrl */
    private static void changeBaseUrl(String url,int baseUrlCode){
        tempBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());
        Client.tempBuilderCode = baseUrlCode;

    }


    public static <T> T build(Class<T> service){

//        if uas Header
        okHttpClient.addInterceptor(fray);

        //increase TimeOut,Default :10000 "10 seconds"
//        okHttpClient
//                .readTimeout(31, TimeUnit.SECONDS)
//                .writeTimeout(31,TimeUnit.SECONDS);

        //lastLine
        if (BuildConfig.DEBUG) {
            if (!okHttpClient.interceptors().contains(log))
                okHttpClient.addInterceptor(log);
        }
        retrofit = builder.client(okHttpClient.build()).build();

        return retrofit.create(service);
    }


    public static <T> T build(Class<T> service,String baseUrl,int baseUrlCode){
        if (baseUrlCode != Client.tempBuilderCode)
            changeBaseUrl(baseUrl,baseUrlCode);
//        if uas Header
        okHttpClient.addInterceptor(fray);

        //increase TimeOut,Default :10000 "10 seconds"
//        okHttpClient
//                .readTimeout(31, TimeUnit.SECONDS)
//                .writeTimeout(31,TimeUnit.SECONDS);

        //lastLine
        if (BuildConfig.DEBUG) {
            if (!okHttpClient.interceptors().contains(log))
                okHttpClient.addInterceptor(log);
        }
        retrofit = tempBuilder.client(okHttpClient.build()).build();

        return retrofit.create(service);
    }
}
