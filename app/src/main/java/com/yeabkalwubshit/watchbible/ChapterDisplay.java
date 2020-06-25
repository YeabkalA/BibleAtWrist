package com.yeabkalwubshit.watchbible;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class ChapterDisplay extends WearableActivity {

    private static final String TAG = "BookSelect";

    private WearableRecyclerView versesRecyclerView;
    private LinearLayoutManager layoutManager;
    private TextView bookChapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_display);

        versesRecyclerView = findViewById(R.id.verses_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        bookChapterView = findViewById(R.id.book_chapter_view);
        versesRecyclerView.setLayoutManager(layoutManager);

        final String currentBookChapterInfo = getIntent().getStringExtra(Consts.BOOK_CHAPTER_EXTRA);
        String[] bookChapter = currentBookChapterInfo.split("\\*");
        String currentBook = bookChapter[0];
        String currentChapter = bookChapter[1];

        bookChapterView.setText(
                BibleReader.getLongVersionBookName(currentBook) + " " + currentChapter);

        List<Verse> allVerses = null;
        try {

            allVerses = BibleReader.getSingleton(getApplicationContext()).
                    getAllVersesOfAChapter(currentBook, currentChapter);
        } catch (IOException | JSONException e) {
            Log.d(TAG, "Reading all chapters from singleton failed: " + e.toString());
        }

        List<GenericTableCellData> tableData = new ArrayList<>();
        for(Verse verseInfo: allVerses) {
            tableData.add(new GenericTableCellData(
                    verseInfo.verseNumber + ". " + verseInfo.verse));
        }

        GenericAdapter adapter = new GenericAdapter(tableData, this) {
            @Override
            public void onClick(String itemClicked) {

            }
        };
        adapter.setId(Consts.ADAPTER_ID_CHAPTER_DISPLAY);

        versesRecyclerView.setAdapter(adapter);

        // Enables Always-on
        setAmbientEnabled();
    }

    private String createBookChapterExtra(String book, String chapter) {
        return book + "*" + chapter;
    }
}
