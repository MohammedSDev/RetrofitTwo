package co.clickapps.retrofittwo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by clickapps on 17/9/17.
 */

public class UserModel {

    @SerializedName("data")
    private Data data;

    //Getter


    public Data getData() {
        return data;
    }

    //class
    public static class  Data{

        @SerializedName("id")
        private String id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("avatar")
        private String avatar;

        //Getter


        public String getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAvatar() {
            return avatar;
        }


    }
}
