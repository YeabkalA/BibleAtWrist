package com.yeabkalwubshit.watchbible;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class BookSelect extends WearableActivity {

    private static final String TAG = "BookSelect";

    private WearableRecyclerView booksRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select);

        try {
            Verse randomVerse = RandomVerseSupplier.getRandomVerseFromSingleton(this);
            System.out.println("Random verse..." + randomVerse.getBook() +
                    ":" + randomVerse.getChapter() + ":" +
                    randomVerse.getVerseNumber() + ":" +
                    randomVerse.getVerse());
        } catch (IOException | JSONException e) {
            System.out.println("Random verse... exception!");
        }

        booksRecyclerView = findViewById(R.id.books_recycler_view);
        booksRecyclerView.setEdgeItemsCenteringEnabled(true);
        booksRecyclerView.setLayoutManager(
                new WearableLinearLayoutManager(this));

        List<String> allBooks = null;
        try {
            allBooks = BibleReader.getSingleton(getApplicationContext()).getAllBooks();
        } catch (IOException | JSONException e) {
            Log.d(TAG, "Reading all chapters from singleton failed: " + e.toString());
        }

        List<GenericTableCellData> tableData = new ArrayList<>();
        GenericAdapter adapter = null;
        try {
            for(String bookStr: allBooks) {
                String bookDisplayText = BibleReader.getLongVersionBookName(this, bookStr);
                tableData.add(new GenericTableCellData(bookDisplayText));
            }

            adapter = new GenericAdapter(tableData, this) {
                @Override
                public void onClick(String itemClicked) {
                    System.out.println("Sthe clicked item is " + itemClicked);
                    try {
                        goToChapterSelect(BibleReader.getShortVersionBookName(
                                BookSelect.this, itemClicked));
                    } catch (IOException | JSONException e) {
                        Log.d(TAG, "Exception in going to chapter select: " + e.toString());
                    }
                }
            };
            booksRecyclerView.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            Log.d(TAG, "Exception in setting adapter: " + e.toString());
        }


        // Enables Always-on
        setAmbientEnabled();
    }

    private void goToChapterSelect(String book) {
        Intent intent = new Intent(this, ChapterSelect.class);
        intent.putExtra(Consts.BOOK_EXTRA, book);
        startActivity(intent);
    }
}
