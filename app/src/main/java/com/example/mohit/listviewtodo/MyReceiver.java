package com.example.mohit.listviewtodo;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Reminder", Toast.LENGTH_LONG).show();

        NotificationManager manager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("mychannelid","TODO channel",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"mychannelid");
        builder.setContentTitle("Time To do IT");
        builder.setContentText(intent.getStringExtra("title"));
        builder.setSmallIcon(R.drawable.ic_playlist_add_black_24dp);
        Notification notification=builder.build();
        manager.notify(1,notification);
    }
}
