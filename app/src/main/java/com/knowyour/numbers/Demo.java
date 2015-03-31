package com.knowyour.numbers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Demo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        findViewById(R.id.number_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.getContext().startActivity(new Intent(v.getContext(), NumberView.class));
            }
        });

        findViewById(R.id.first_run_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.getContext().startActivity(new Intent(v.getContext(), FirstRun.class));
            }
        });

        findViewById(R.id.reminder_return_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PendingIntent changeNotifications = TaskStackBuilder.create(Demo.this)
                        .addNextIntent(new Intent(Demo.this, Demo.class))
                        .getPendingIntent(0, 0);
                final Notification notification = new Notification.Builder(Demo.this)
                        .setSmallIcon(R.drawable.numbers_logo)
                        .setContentTitle("Time to refresh that number knowledge")
                        .setContentText("Its be N days lets keep that number on the front of your child's mind!")
                        .addAction(android.R.drawable.ic_menu_delete, "Change Time", changeNotifications)
                        .addAction(android.R.drawable.ic_menu_zoom, "Start", changeNotifications)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .build();

                final NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, notification);
            }
        });
    }
}
