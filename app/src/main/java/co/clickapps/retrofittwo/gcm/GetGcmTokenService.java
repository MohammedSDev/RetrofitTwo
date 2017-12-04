package co.clickapps.retrofittwo.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import co.clickapps.retrofittwo.R;

/**
 * Created by clickapps on 28/11/17.
 * register app & get token from gcm.
 */

public class GetGcmTokenService extends IntentService {


    final String TAG = "mud";
    public GetGcmTokenService() {
        super(" Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        try {
            Log.d(TAG, "onHandleIntent: ");
            InstanceID instance = InstanceID.getInstance(this);
//            String token = instance.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            String token = instance.getToken("301865693938", GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            subscribe(token,"/topics/test_topi");
            Log.d(TAG, "onHandleIntent: Token: " + token);
            Log.d(TAG, "onHandleIntent: id: " + instance.getId());
            Log.d(TAG, "onHandleIntent: ........................................//$%^");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * subscribe to specific topic.
     * name pattern "/topics/[a-zA-Z0-9-_.~%]+"
     * @param token gcm token.
     * @param topicName topic to subscribe to it.
     *
    * */
    private void subscribe(String token,String topicName) throws IOException {

        GcmPubSub sub = GcmPubSub.getInstance(this);
        sub.subscribe(token,topicName,null);
        Log.d(TAG, "subscribe: Successful. topic: " + topicName);
    }
}
