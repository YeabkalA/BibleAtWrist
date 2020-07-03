package com.yeabkalwubshit.watchbible;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class RandomVerseDisplay extends WearableActivity {

    private TextView verseInfoDisplay;
    private RecyclerView verseDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_verse_display);

        verseInfoDisplay = findViewById(R.id.randome_verse_book_chapter_verse);
        verseDisplay = findViewById(R.id.random_verse_displaying_table);
        verseDisplay.setLayoutManager(new LinearLayoutManager(this));


        Verse randomVerse = null;
        try {
            randomVerse = RandomVerseSupplier.getRandomVerseFromSingleton(this);
        } catch (IOException | JSONException e) {
            return;
        }

        verseInfoDisplay.setText(randomVerse.getBook() + " " + randomVerse.getChapter() + ":" + randomVerse.getVerseNumber());

        List<GenericTableCellData> tableData = new ArrayList<>();
        tableData.add(new GenericTableCellData(randomVerse.getVerse()));


        GenericAdapter adapter = new GenericAdapter(tableData, this) {
            @Override
            public void onClick(String itemClicked) {

            }
        };
        adapter.setId(Consts.ADAPTER_ID_RANDOM_VERSE_DISPLAY);
        verseDisplay.setAdapter(adapter);

        // Enables Always-on
        setAmbientEnabled();
    }
}
