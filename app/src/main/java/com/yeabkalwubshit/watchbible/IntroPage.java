package com.yeabkalwubshit.watchbible;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

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

        // Enables Always-on
        setAmbientEnabled();
    }
}
