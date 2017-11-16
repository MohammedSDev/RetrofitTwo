package co.clickapps.retrofittwo.social_media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import co.clickapps.retrofittwo.R;
import retrofit2.Call;

public class TwitterActivity extends AppCompatActivity {

    private final String TAG = "twitter mud";

    private TwitterLoginButton loginButton;
    BroadcastReceiver receiver;
    Button tweetB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_twitter);


        tweetB = findViewById(R.id.login_twitter_tweet_button);
        loginButton = findViewById(R.id.login_twitter_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.d(TAG, "success: UserId:" + result.data.getUserId());
                Log.d(TAG, "success: UserName:" + result.data.getUserName());
                Log.d(TAG, "success: AuthToken:" + result.data.getAuthToken());


                //Request Email
//        TwitterCore.getInstance().getSessionManager().getActiveSession()
                new TwitterAuthClient().requestEmail(result.data, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {

                        Log.d(TAG, "success: Email: " + result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                        Log.d(TAG, "failure: EmailError: " + exception.getMessage());

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d(TAG, "failure: " + exception.getMessage());
            }
        });


        tweetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet("Haaa");
            }
        });

        //you can get AuthToken & UserId later using this line
//        TwitterCore.getInstance().getSessionManager().getActiveSession().


    }

    private void tweet(String mes){
//        long userId = TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId();
//        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();
        final Intent intent = new ComposerActivity.Builder(this)
                .session(session)
                .text("Have a good day, for you")
                .hashtags("#me")
                .createIntent();
        startActivity(intent);


       /* StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<Tweet> call = statusesService.show(userId, null, null, null);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                //Do something with result

            }

            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });
*/
    }

    private void bcReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.twitter.sdk.android.tweetcomposer.UPLOAD_SUCCESS");
        filter.addAction("com.twitter.sdk.android.tweetcomposer.UPLOAD_FAILURE");
        filter.addAction("com.twitter.sdk.android.tweetcomposer.TWEET_COMPOSE_CANCEL");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    public static void startThisActivity(Activity activity){
        Intent x = new Intent(activity,TwitterActivity.class);
        x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(x);
    }


    @Override
    protected void onStart() {
        super.onStart();
        bcReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }
}
