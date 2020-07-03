package com.yeabkalwubshit.watchbible;

import android.content.Context;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomVerseSupplier {
    final Random random = new Random();
    final List<Verse> verses = new ArrayList<>();

    public RandomVerseSupplier(Context context) throws IOException, JSONException {
        InputStream inputStream = context.getResources().getAssets().open("random_verse_list.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while((line = br.readLine()) != null) {
            System.out.println("The line is " + line);
            String[] splitLine = line.split(":");
            String book = splitLine[0];
            String chapter = splitLine[1];
            String verseNumber = splitLine[2];

            Verse verse = new Verse();
            verse.setBook(BibleReader.getLongVersionBookName(context, book));
            verse.setChapter(chapter);
            verse.setVerseNumber(verseNumber);
            verse.setVerse(BibleReader.getSingleton(context).getVerseText(book, chapter, verseNumber));
            verses.add(verse);
        }
    }

    // Chapter is in long form.
    Verse getRandomVerse() {
        return verses.get(random.nextInt(verses.size()));
    }

    static Verse getRandomVerseFromSingleton(Context context) throws IOException, JSONException {
        if (SINGLETON == null) {
            SINGLETON = new RandomVerseSupplier(context);
        }
        return SINGLETON.getRandomVerse();
    }

    static RandomVerseSupplier SINGLETON;


}
