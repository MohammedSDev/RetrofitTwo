package co.clickapps.retrofittwo.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by clickapps on 17/9/17.
 */

public class PhoneModel extends RealmObject{

    @Required
    @PrimaryKey
    private String id;
    @Required
    private String number;



    //Getter

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    //Setter

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
