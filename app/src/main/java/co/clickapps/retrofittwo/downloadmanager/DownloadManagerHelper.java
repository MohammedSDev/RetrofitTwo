package co.clickapps.retrofittwo.downloadmanager;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.MimeTypeFilter;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import co.clickapps.retrofittwo.helpful.AppUtils;

/**
 * Created by clickapps on 30/11/17.
 */

public class DownloadManagerHelper {



    private DownloadManager mDownloadManager;
    private BroadcastReceiver mDownloadReceiver;
    private BroadcastReceiver mDownloadClickReceiver;
    private Context context;
    String statusText = "";
    String reasonText = "";

    private final String TAG = "mud";




    public DownloadManagerHelper(Context context) {
        this.mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = context;
    }

    public long downloadFile(Uri fileUri) {
        DownloadManager.Request req = new DownloadManager.Request(fileUri);

        req.setTitle("Title FileDownload");
        req.setDescription("Desc File Download");
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "fileSubPath.jpg");
//        req.setVisibleInDownloadsUi(false);

        return mDownloadManager.enqueue(req);
    }

    public void CheckStatus(long downloadId) {

        DownloadManager.Query ImageDownloadQuery = new DownloadManager.Query();
        //set the query filter to our previously Enqueued download
        ImageDownloadQuery.setFilterById(downloadId);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = mDownloadManager.query(ImageDownloadQuery);
        if (cursor.moveToFirst()) {
            DownloadStatus(cursor, downloadId);
        }
    }

    private String DownloadStatus(Cursor cursor, long downloadId) {

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
//        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//        String filename = cursor.getString(filenameIndex);


        statusText = "";
        reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:";
                break;
        }

        return statusText;
    }

    public void initializeReceiver(Context context){
        unRegisterReceiver(context);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        mDownloadReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                //check if the broadcast message is for our enqueued download
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.d("mud", "onReceive: complete download"  + longExtra + "............................//$#%");

            }
        };

        //user click on download progress
        IntentFilter filterClick = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);

        mDownloadClickReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                //check if the broadcast message is for our enqueued download
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.d("mud", "onReceive: running download clicked "  + longExtra + "............................//$#%");

            }
        };

        context.registerReceiver(mDownloadReceiver, filter);
        context.registerReceiver(mDownloadClickReceiver, filterClick);
    }

    public void unRegisterReceiver(Context context) {
        if (mDownloadReceiver != null){
            context.unregisterReceiver(mDownloadReceiver);
            context.unregisterReceiver(mDownloadClickReceiver);
            mDownloadReceiver = null;
            mDownloadClickReceiver = null;
        }
    }

    public void cancel(long id){
        int xx = mDownloadManager.remove(id);
        Log.d("mud", "cancel: id: " + xx);
    }

    public void openDownloadFile(long id){
        try {
            Log.d(TAG, "openDownloadFile: start open file");
            mDownloadManager.openDownloadedFile(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("mud", "openDownloadFile: ");
        }
    }


    public Uri showFileContent(long id) {
        Uri uri = mDownloadManager.getUriForDownloadedFile(id);
        Log.d(TAG, "showFileContent: Id: " + id);
        Log.d(TAG, "showFileContent: Uri: " + uri);
        Log.d(TAG, "showFileContent: Uri path: " + uri.getPath());
        Log.d(TAG, "showFileContent: UriType: " + context.getContentResolver().getType(uri));
        Log.d(TAG, "showFileContent: Extension: " + MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri)) );
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setData(uri);
//        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

       /* Intent shareIntent = new Intent(); //work fine
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image*//*");*/

        try {
            Log.d(TAG, "showFileContent: getPath: " + AppUtils.getFilePath(context,uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Uri uriForFile =
                null;
        try {
            uriForFile = FileProvider.getUriForFile(context
                    , "co.clickapps.retrofittwo.fileprovider"
                    , new File(AppUtils.getFilePath(context,uri)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        context.grantUriPermission(context.getPackageName(),uriForFile,Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uriForFile);
//        shareIntent.setData(uriForFile);
        shareIntent.setDataAndType(uriForFile,"image/*");

//        shareIntent.setType("image/*");

//        Intent intent = Intent.createChooser(target, "open download file");
        Intent intent = Intent.createChooser(shareIntent, "open download file");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "can't open file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return uri;
    }



    //Getter

    public String getStatusText() {
        return statusText;
    }

    public String getReasonText() {
        return reasonText;
    }
}
