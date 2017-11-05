package co.clickapps.retrofittwo.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by clickapps on 23/9/17.
 */

public class MyJsonModel {


//    @SerializedName("success")
//    public Boolean success;
    private String message;
    private String falsemessage;
//    @SerializedName("error_code")
//    public Integer errorCode;
//    @SerializedName("data")
//    public Object data;



    //Getter

    public String getMessage() {
        return message;
    }
}
