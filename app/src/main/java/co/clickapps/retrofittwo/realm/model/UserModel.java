package co.clickapps.retrofittwo.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by clickapps on 17/9/17.
 */

public class UserModel extends RealmObject {



    @PrimaryKey
    private String id;
    private String name;
    private String birthDay;
    private RealmList<PhoneModel> phones;


    //constructors


    public UserModel() {
    }

    public UserModel(String id, String name, String birthDay, RealmList<PhoneModel> phones) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.phones = phones;
    }

    @Ignore
    private String age;



    //Getter

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public RealmList<PhoneModel> getPhones() {
        return phones;
    }

    public String getAge() {
        return age;
    }


    //Setter

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setPhones(RealmList<PhoneModel> phones) {
        this.phones = phones;
    }
}
