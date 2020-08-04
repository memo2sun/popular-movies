package net.sunyounglee.browsemovies.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.ui.MainActivity;


public class NotificationUtils {

    private static final String TAG = NotificationUtils.class.getSimpleName();

    private static final String MOVIE_SYNC_NOTIFICATION_CHANNEL_ID = "movie_sync_notification_channel";
    private static final int MOVIE_SYNC_NOTIFICATION_ID = 1004;
    private static final int ACTION_SYNC_PENDING_INTENT_ID = 1;

    public static void notifyUser(Context context) {

        Log.d(TAG, "notifyUser method was called");
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    MOVIE_SYNC_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        String notificationTitle = context.getString(R.string.notification_title);
        String notificationText = context.getString(R.string.notification_body);
        int smallArtResourceId = R.drawable.ic_baseline_sync_24;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, MOVIE_SYNC_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(smallArtResourceId)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(MOVIE_SYNC_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                ACTION_SYNC_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
