package co.clickapps.retrofittwo.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by clickapps on 20/11/17.
 *
 * listen to token changes, and if it happen.start service for get gcm token
 */

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private final String TAG  = "mud IDListener";

    

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Log.d(TAG, "handleIntent: ");
    }


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.d(TAG, "onTokenRefresh: ");

        Intent gcmIntent = new Intent(this, GetGcmTokenService.class);
        startService(gcmIntent);
    }
}
