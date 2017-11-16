package co.clickapps.retrofittwo.social_media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileManager;
import com.facebook.ProfileTracker;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.Arrays;

import co.clickapps.retrofittwo.R;

public class SocialActivity extends AppCompatActivity {

    private final String TAG = "fbmud";
    LoginButton mFBLogin;
    Button bpost;
    private final int APP_REQUEST_CODE = 10020;

    CallbackManager manager;
    ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bpost = findViewById(R.id.fab_post);

        bpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post("");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (com.facebook.AccessToken.getCurrentAccessToken() != null) {
                    Profile.fetchProfileForCurrentAccessToken();
                    Log.d(TAG, "onClick: fetchProfileForCurrentAccessToken");

                    // add additional permissions
//                    LoginManager.getInstance().logInWithReadPermissions(SocialActivity.this, Arrays.asList("user_friends"));

                    // get more User info
                    getProfileFields(com.facebook.AccessToken.getCurrentAccessToken());
                }

            }
        });


        manager = CallbackManager.Factory.create();
        mFBLogin = findViewById(R.id.facebook_login);
        mFBLogin.setReadPermissions("email");
        mFBLogin.registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getPermissions().toString());


                if (loginResult.getAccessToken().getPermissions().toString().contains("friend")){

                }
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });




        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null)
                    Log.d(TAG, "onCurrentProfileChanged: Url:" + currentProfile.getLinkUri());

            }
        };
        if (!profileTracker.isTracking()) {
            profileTracker.startTracking();
            Log.d(TAG, "onCreate: startedTracking");
        }


    }


    private void post(String message){





        final Bundle bundle = new Bundle();
        bundle.putString("message","Good Develop Day");
        bundle.putString("link","http://www.ya.com");
        LoginManager.getInstance().logInWithPublishPermissions(this,Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                new GraphRequest(loginResult.getAccessToken()
                        , "me/feed"
                        , bundle
                        , HttpMethod.POST
                        , new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            if (response.getJSONObject().get("id") != null) {
                                Log.d(TAG, "onCompleted: post Done");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
            }
        });


    }

    private AccessToken checkAccountKitLogin() {
        final AccessToken accessToken = AccountKit.getCurrentAccessToken();

        if (accessToken != null) {
            //Handle Returning User
            Toast.makeText(this, "User Alrady login", Toast.LENGTH_LONG).show();
            showCurrentAccountInfo();

        } else {
            //Handle new or logged out user
            Toast.makeText(this, "PLease Login", Toast.LENGTH_SHORT).show();
        }
        return accessToken;
    }

    public void phoneLogin(View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);

        // ... perform additional configuration if you need...



        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }


    public void emailLogin(final View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.EMAIL,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.CODE
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }


    private void showCurrentAccountInfo(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();

                // Get email
                String email = account.getEmail();

                Toast.makeText(SocialActivity.this, "ID:" + accountKitId
                        + "\nPhone:" + phoneNumberString
                        + "\nEmail:" + email
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
            }



        });
    }


    private void getProfileFields(com.facebook.AccessToken token){
        Bundle bundle = new Bundle();
        bundle.putString("fields","id,name,gender");
        bundle.putInt("limit",4);

        new GraphRequest(token
                , "me/" //for Api Reference https://developers.facebook.com/docs/graph-api/reference/
                , bundle
                , HttpMethod.GET
                , new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                if (response.getError()!= null) {
                    Log.d(TAG, "onCompleted: Graph Request,Error: " + response.getError());

                    return;
                }
                try {
                    Log.d(TAG, "onCompleted: Gender: " + response.getJSONObject().getString("gender"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        ).executeAsync();
    }


    @Override
    protected void onActivityResult(final int requestCode,final int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...

            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
        else {
            manager.onActivityResult(requestCode,resultCode,data);
        }
    }


    public static void startThisActivity(Activity activity){
        Intent x = new Intent(activity,SocialActivity.class);
        x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(x);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }



}
