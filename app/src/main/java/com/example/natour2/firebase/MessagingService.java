package com.example.natour2.firebase;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.natour2.HomeActivity;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.model.User;
import com.example.natour2.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {

    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
/*
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }




    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        User user = new User();
        user.setUsername(remoteMessage.getData().get(Constants.KEY_USER_ID));
        user.setUsername(remoteMessage.getData().get(Constants.KEY_NAME));
        //user.se = remoteMessage.getData().get(Constants.KEY_FCM_TOKEN);

        int notificationId = new Random().nextInt();
        String channelId = "naTour21_channelId";


        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.KEY_USER, user);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        RemoteMessage.Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(getResources().getDrawable().drawable.ic_notification)
                .setContentTitle(user.name)
                .setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE))
                .setGroup("FIRST_GROUP_ID")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(user.name);
        builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                remoteMessage.getData().get(Constants.KEY_MESSAGE)
        ));
        builder.setGroup("FIRST_GROUP_ID");
        builder.setGroupSummary(true);

        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence channelName = "Chat Message";
            String channelDescription = "This notification channel is used for chat message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        //notificationManagerCompat.notify(notificationId, builder.build());
        notificationManagerCompat.notify(notificationId, notification);
        summaryNotification();
    }

    private void summaryNotification(){
        String channelId = "naTour21_channelId";
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setGroup("FIRST_GROUP_ID")
                .setGroupSummary(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, notification);
    }

 */
}
