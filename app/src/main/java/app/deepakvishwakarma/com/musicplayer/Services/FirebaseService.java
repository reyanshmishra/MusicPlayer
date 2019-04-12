package app.deepakvishwakarma.com.musicplayer.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.SearchActivity;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServic";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = "";
        String message = "";
        String click_action = "";
        String url = "";
        String jsonMessage = "";
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                jsonMessage = data.getString("URL");
                Log.d(TAG, "onMessageReceived: \n" + "Extra Information: " + jsonMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*
            * In Oreo if the push notification is not received in app killed mode then turn off the battery optimization and resolve it.
             * */
        }

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle(); //get title
            message = remoteMessage.getNotification().getBody(); //get message
            click_action = remoteMessage.getNotification().getClickAction(); //get click_action
            // url = remoteMessage.getData().get("url");

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);
            // Log.d(TAG, "Message Notification url: " + url);
        }
        sendNotification(title, message, click_action, jsonMessage);
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title, String messageBody, String click_action, String url) {
        Intent intent;

        if (click_action.equals("SOMEACTIVITY")) {
            intent = new Intent(this, SearchActivity.class);
            if (url != null) {
                intent.putExtra("URL", url);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {

            intent = new Intent(this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(111 /* ID of notification */, notificationBuilder.build());
    }
}