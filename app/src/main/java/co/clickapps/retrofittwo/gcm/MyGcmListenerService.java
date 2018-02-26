package co.clickapps.retrofittwo.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by clickapps on 20/11/17.
 * listen to messages received. snd show them to user or whatever you want.
 */

public class MyGcmListenerService extends GcmListenerService {

    private final String TAG  = "mud service";

    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        Log.d(TAG, "onMessageReceived: " + s);
        Log.d(TAG, "onMessageReceived:message fromKey:: " + bundle.getString("message"));
        Log.d(TAG, "onMessageReceived:BUndle bundle:: " + bundle.toString());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages: ");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d(TAG, "onMessageSent: " + s);
    }

    @Override
    public void onSendError(String s, String s1) {
        super.onSendError(s, s1);
        Log.d(TAG, "onSendError: " + s + ",|," + s1);
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Log.d(TAG, "handleIntent: ");
    }
}
