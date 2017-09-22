package co.clickapps.retrofittwo.service;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by clickapps on 22/9/17.
 */

public class MyServiceHelper implements MyServiceCallBack {

    private static MyServiceHelper INSTANT;
    private Context context;
    private MyService myService;
    private boolean isStart;
    private boolean isBind;
    private ServiceConnection connection;
    Intent intent ;
    private Bundle bundle;
    public static MyServiceHelper getInstance(Context context) {
        if (INSTANT ==  null)
            INSTANT = new MyServiceHelper(context);
        return INSTANT;
    }

    public MyServiceHelper(Context context) {
        this.context = context;
        intent = new Intent(context,MyService.class);
    }

    public void startMyService(){
//        if (!isStart){
        Log.d("mud", "startMyService: ");
        bundle = new Bundle();
        bundle.putInt("count",10);
        bundle.putInt("taskId",101);
        intent.putExtras(bundle);
        context.startService(intent);
//            isStart = true;
//        }
    }

    @Override
    public void ServiceCallback(int taskId, boolean state, String data) {
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }
}
