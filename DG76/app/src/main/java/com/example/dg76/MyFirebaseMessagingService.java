package com.example.dg76;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if the remoteMessage contains a notification payload
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            String title = notification.getTitle();
            String body = notification.getBody();

            // Display the notification
            showNotification(title, body);
        }
    }

    private void showNotification(String title, String body) {
        String channelId = "my_channel_id"; // Create a unique channel ID for the notification

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Display the notification
        if (notificationManager != null) {
            notificationManager.notify(/* Notification ID */ 0, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(String token) {
        // Handle token refresh here
        // You can send the new token to your server or perform any other necessary actions
    }
}
