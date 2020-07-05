package com.yeabkalwubshit.watchbible;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class IntroPage extends WearableActivity {

    private WearableRecyclerView options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        showTimePickerDialog();

        options = findViewById(R.id.intro_recycler_view);
        options.setEdgeItemsCenteringEnabled(true);
        options.setLayoutManager(new WearableLinearLayoutManager(this));

        List<GenericTableCellData> tableData = new ArrayList<>();
        tableData.add(new GenericTableCellData("Read"));
        tableData.add(new GenericTableCellData("Verse"));

        GenericAdapter adapter = new GenericAdapter(tableData, this) {
            @Override
            public void onClick(String itemClicked) {
                switch (itemClicked) {
                    case "Read": {
                        Intent intent = new Intent(IntroPage.this, BookSelect.class);
                        startActivity(intent);
                        break;
                    }
                    case "Verse": {
                        Intent intent = new Intent(IntroPage.this, RandomVerseDisplay.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        };

        options.setAdapter(adapter);

        createNotificationChannel();
        //NotificationTool.sendNotification(this);

        // Enables Always-on
        setAmbientEnabled();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MYCHannel";
            String description = "First channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showTimePickerDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                NotificationTool.scheduleNotification(IntroPage.this, hourOfDay, minute);
            }
        }, 2,2,false);
        dialog.show();
    }
}
