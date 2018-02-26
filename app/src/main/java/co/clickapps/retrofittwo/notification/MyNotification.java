package co.clickapps.retrofittwo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import co.clickapps.retrofittwo.MainActivity;
import co.clickapps.retrofittwo.R;

/**
 * Created by clickapps on 4/12/17.
 */

public class MyNotification {



    NotificationManagerCompat mManager;
//    NotificationManager mManager;
    NotificationCompat.Builder mBuilder;

    public MyNotification(Context context) {
        this.mManager = NotificationManagerCompat.from(context);
//        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context,"channel id");
    }

    public void createBasicNotification(int icon, String title, String text,Context context){

        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,2000,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setSubText("sub text")
                .setTicker("Tiker") /*this no longer seen in 5.0 and above*/
                .setContentInfo("Content info")
        ;

    }

    public void setLargeIcon(Bitmap icon,int color){
        mBuilder.setLargeIcon(icon)
                .setColor(color);
    }

    public void showNotification(){
        mManager.notify(1046,mBuilder.build());
    }


    public void bigTextStyle(){
        NotificationCompat.BigTextStyle big = new NotificationCompat.BigTextStyle();
        big.setBigContentTitle("Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title"
         + "Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title"
        + "Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title"
        + "Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title"
        + "Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title"
        + "Big content title Big content title Big content title Big content title Big content title Big content titleBig content title Big content title Big content title Big content title");
        big.bigText("Big Text Big Text Big Text Big Text Big Text Big Text Big Text Big Text Big Text Big Text Big Text Big Text");
        big.setSummaryText("BIg summary");
        mBuilder.setStyle(big);
    }

    public void inStyle(){
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setSummaryText("summary text");
        inboxStyle.setBigContentTitle("BigContentTitle BigContentTitle BigContentTitle");
        inboxStyle.addLine("addLine 1, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 2, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 3, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 4, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 5, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 6, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 7, addLine addLine addLine addLine");/*support till seventh line*/
        inboxStyle.addLine("addLine 8, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 9, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 10, addLine addLine addLine addLine");
        inboxStyle.addLine("addLine 11, addLine addLine addLine addLine");
        mBuilder.setStyle(inboxStyle);
    }


    public void bigPictureStyle(Bitmap bitmapBig,Bitmap bitmapIcon){
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("BigContentTitle BigContentTitle BigContentTitle BigContentTitle v");
        style.setSummaryText("SummaryText SummaryText SummaryText SummaryText");
        style.bigPicture(bitmapBig);
        style.bigLargeIcon(bitmapIcon);
        mBuilder.setStyle(style);
    }


    public void newExampleCreateNotification(Context context){
        // The id of the channel.
        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications_none)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// mNotificationId is a unique integer your app uses to identify the
// notification. For example, to cancel the notification, you can pass its ID
// number to NotificationManager.cancel().
        mNotificationManager.notify(1049, mBuilder.build());
    }







}
