package com.yeabkalwubshit.watchbible;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import androidx.core.app.NotificationManagerCompat;

public class NotificationTool {
    static final String CHANNEL_ID = "0";

    public static void sendNotification(Context context) {
        Verse verse = null;
        try {
            verse = RandomVerseSupplier.getRandomVerseFromSingleton(context);
        } catch (Exception e) {
            return;
        }

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);
        builder.setContentTitle(verse.getHeader());
        builder.setContentText(verse.getVerse());
        builder.setSmallIcon(R.drawable.cross_transparent);

        Intent notifyIntent = new Intent(context, RandomVerseDisplay.class);
        notifyIntent.putExtra(Verse.INTENT_HEADER_EXTRA_KEY, verse.getHeader());
        notifyIntent.putExtra(Verse.INTENT_TEXT_EXTRA_KEY, verse.getVerse());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(3, notificationCompat);
    }

    public static void scheduleNotification(Context context, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
