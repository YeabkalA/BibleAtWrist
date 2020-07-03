package com.yeabkalwubshit.watchbible;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableRecyclerView;

public class ChapterSelect extends WearableActivity {

    private static final String TAG = "BookSelect";

    private WearableRecyclerView chaptersRecyclerView;
    private LinearLayoutManager layoutManager;
    private TextView bookNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_select);

        chaptersRecyclerView = findViewById(R.id.chapters_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        bookNameView = findViewById(R.id.book_name_view);
        chaptersRecyclerView.setLayoutManager(layoutManager);

        final String currentBook = getIntent().getStringExtra(Consts.BOOK_EXTRA);

        try {
            bookNameView.setText(BibleReader.getLongVersionBookName(this, currentBook));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> allChapters = null;
        try {
            System.out.println("Current book found to be " + currentBook);
            allChapters = BibleReader.getSingleton(getApplicationContext()).
                    getAllChaptersOfABook(currentBook);
        } catch (IOException | JSONException e) {
            Log.d(TAG, "Reading all chapters from singleton failed: " + e.toString());
        }

        List<GenericTableCellData> tableData = new ArrayList<>();
        for(String chapterStr: allChapters) {
            tableData.add(new GenericTableCellData(chapterStr));
        }

        GenericAdapter adapter = new GenericAdapter(tableData, this) {
            @Override
            public void onClick(String itemClicked) {
                goToChapterDisplay(currentBook, itemClicked);

            }
        };
        chaptersRecyclerView.setAdapter(adapter);

        // Enables Always-on
        setAmbientEnabled();
    }

    private String createBookChapterExtra(String book, String chapter) {
        return book + "*" + chapter;
    }

    private void goToChapterDisplay(String book, String chapter) {
        Intent intent = new Intent(this, ChapterDisplay.class);
        intent.putExtra(Consts.BOOK_CHAPTER_EXTRA, createBookChapterExtra(book, chapter));
        startActivity(intent);
    }
}
