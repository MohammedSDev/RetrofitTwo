package co.clickapps.retrofittwo.realm.model;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import co.clickapps.retrofittwo.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmActivity extends AppCompatActivity {

    private final String TAG = "mud";


    Button buttonOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        buttonOne = findViewById(R.id.button);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
//                insertInUsersync();
//                insertInBooksync();
//                insertInBooks();
                insertInUser();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onClick: start read users");
                        readUsers();
                    }
                },1200);
            }
        });
    }



    private void insertInBooks(){


        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BookModel book;
                BookModel bookModel;

                for (int j = 0; j < 5; j++) {


                    RealmResults<BookModel> all = realm.where(BookModel.class).findAll();
                    List<BookModel> bookModels = realm.copyFromRealm(all);
                    for (int i = 0; i < 2000; i++) {

                        if (bookModels.size() > i) {
                            bookModel = bookModels.get(i);
                            realm.insertOrUpdate(bookModel);
                        }
                        book = new BookModel("Title", "author", "2017", "G BookLibrary", 125);
                        realm.insertOrUpdate(book);
                        if (i % 1000 == 0)
                            Log.d(TAG, "@headsUp: " + i + "books inserted");
                    }
                    Log.d(TAG, "execute: 2000 books inserted");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: ");
            }
        });
    }



    private void insertInUser(){


        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserModel book;
                UserModel userModel;

                for (int j = 0; j < 5; j++) {


                    RealmResults<UserModel> all = realm.where(UserModel.class).findAll();
                    List<UserModel> userModels = realm.copyFromRealm(all);

                    for (int i = 0; i < 2000; i++) {

                        if (userModels.size() > i) {
                            userModel = userModels.get(i);
                            realm.insertOrUpdate(userModel);
                        }

                        book = new UserModel(i +"", "name " + i, "10-12-2017",null);
                        realm.insertOrUpdate(book);
                        if (i % 1000 == 0)
                            Log.d(TAG, "@headsUp: " + i + "User inserted");
                    }
                    Log.d(TAG, "execute: 2000 User inserted");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: ");
            }
        });
    }

    private void insertInUsersync(){
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                UserModel user;
                for (int i = 0; i < 17000; i++) {
                    user = new UserModel(i +"", "name " + i, "10-12-2017",null);
                    realm.insertOrUpdate(user);

                    if (i % 1000 == 0)
                        Log.d(TAG, "@headsUp: " + i + "User inserted");
                }

                Log.d(TAG, "execute: 7000 User inserted");
            }
        });
    }

    private void insertInBooksync(){
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                BookModel user;
                for (int i = 0; i < 17000; i++) {
                    user = new BookModel("Title", "author", "2017", "G BookLibrary", 127);
                    realm.insertOrUpdate(user);

                    if (i % 1000 == 0)
                        Log.d(TAG, "@headsUp: " + i + "Books inserted");
                }

                Log.d(TAG, "execute: 7000 Books inserted");
            }
        });
    }


    private void readUsers(){
        RealmResults<UserModel> all = Realm.getDefaultInstance().where(UserModel.class).findAll();
        List<UserModel> userModels = Realm.getDefaultInstance().copyFromRealm(all);

        for (int i = 0; i < userModels.size(); i++) {
            if (i % 100 == 0)
                Log.d(TAG, "readUsers: userName: " + userModels.get(i).getName());
        }
    }
}
