package co.clickapps.retrofittwo.social_media;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import co.clickapps.retrofittwo.R;

public class GoogleSinginActivity extends AppCompatActivity {

    String TAG = "google signin mud";
    SignInButton signInButton;

    GoogleSignInClient client;
    int GOOGLE_SIGNIN_REQUECT_CODE = 1501;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_singin);

        signInButton = findViewById(R.id.google_signin_button);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("964215346130-91rtpdu7jo69rvqh91g42e8l905k7d98.apps.googleusercontent.com")
                .build();


        client = GoogleSignIn.getClient(this,options);

        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(this);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleSignIn.getLastSignedInAccount(GoogleSinginActivity.this) == null) {
                    Log.d(TAG, "onClick: start login");
                    Toast.makeText(GoogleSinginActivity.this, "Start Login", Toast.LENGTH_SHORT).show();
                    Intent intent = client.getSignInIntent();
                    startActivityForResult(intent, GOOGLE_SIGNIN_REQUECT_CODE);
                } else {
                    client.signOut().addOnCompleteListener(GoogleSinginActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "onComplete: SiginOut");
                            Log.d(TAG, "onComplete: " + GoogleSignIn.getLastSignedInAccount(GoogleSinginActivity.this));
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GOOGLE_SIGNIN_REQUECT_CODE){
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount result = task.getResult(ApiException.class);
                Log.d(TAG, "onActivityResult: getId: " + result.getId());
                Log.d(TAG, "onActivityResult: getIdToken: " + result.getIdToken());
                Log.d(TAG, "onActivityResult: DisplayName: " + result.getDisplayName());
                Log.d(TAG, "onActivityResult: getEmail: " + result.getEmail());
                Log.d(TAG, "onActivityResult: getFamilyName: " + result.getFamilyName());
                Log.d(TAG, "onActivityResult: getGivenName: " + result.getGivenName());
                Log.d(TAG, "onActivityResult: getServerAuthCode: " + result.getServerAuthCode());
                Log.d(TAG, "onActivityResult: getPhotoUrl: " + result.getPhotoUrl());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}
