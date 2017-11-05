package co.clickapps.retrofittwo.json;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by clickapps on 23/9/17.
 */

public class MyJson {

    public static final String TAG = "mud";

    public static void convert(String json){

//        json = "{\"success\":false,\"message\":\"Phone number has already been taken\",\"error_code\":422,\"data\":{}}";
        json = "{\"success\":true,\"message\":\"User created successfully\",\"data\":{\"id\":36,\"email\":\"user01@g.com\",\"full_name\":\"user01user01\",\"username\":\"user01\",\"job_title\":null,\"phone_number\":\"1234567891\",\"address\":null,\"about_me\":null,\"date_of_birth\":\"2007-09-23\",\"website\":null,\"facebook_url\":null,\"twitter_url\":null,\"snapchat_url\":null,\"linkedin_url\":null,\"instagram_url\":null}}";

        Gson gson = new Gson();
        MyJsonModel myJsonModel = gson.fromJson(json, MyJsonModel.class);
        Log.d(TAG, "convert: " + myJsonModel.getMessage());
    }
}
