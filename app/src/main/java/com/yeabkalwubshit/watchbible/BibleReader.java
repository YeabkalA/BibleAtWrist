package com.yeabkalwubshit.watchbible;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BibleReader {
    private final Map<String, Map<String, Map<String, String>>> bibleData;
    private final List<String> booksInOrder;
    private final Map<String, String> abbrevToLongMap;
    private final Map<String, String> longToAbbrevMap;

    // Obtained from https://www.sacred-texts.com/bib/osrc/index.htm
    private static final String BIBLE_DATA_SOURCE = "kjvdat.txt";
    private static BibleReader SINGLETON;


    public BibleReader(Context context) throws IOException, JSONException {
        bibleData = new HashMap<>();
        booksInOrder = new ArrayList<>();
        abbrevToLongMap = new HashMap<>();
        longToAbbrevMap = new HashMap<>();

        InputStream inputStream = context.getResources().getAssets().open("kjvdat.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        String prevBook = null;

        while((line = br.readLine()) != null) {
            int firstIndexOfSpace = line.indexOf(' ');
            String verseInfo = line.substring(0, firstIndexOfSpace);
            String verse = line.substring(firstIndexOfSpace + 1);

            String[] verseInfoSplit = verseInfo.split("\\|");
            String book = verseInfoSplit[0].toLowerCase();
            String chapterNumber = verseInfoSplit[1];
            String verseNumber = verseInfoSplit[2];

            if (!book.equals(prevBook)) {
                prevBook = book;
                booksInOrder.add(book);
            }

            if (!bibleData.containsKey(book)) {
                bibleData.put(book, new HashMap<String, Map<String, String>>());
            }

            Map<String, Map<String, String>> chaptersData = bibleData.get(book);
            if (!chaptersData.containsKey(chapterNumber)) {
                chaptersData.put(chapterNumber, new HashMap<String, String>());
            }

            Map<String, String> versesData = bibleData.get(book).get(chapterNumber);
            versesData.put(verseNumber, verse);
        }

        inputStream = context.getResources().getAssets().open("book_names_short_version_json");
        br = new BufferedReader(new InputStreamReader(inputStream));
        line = null;
        StringBuffer jsonString = new StringBuffer();

        while((line = br.readLine()) != null) {
            jsonString.append(line);
        }

        JSONObject verseAbbrJsonObject = new JSONObject(jsonString.toString());
        Iterator<String> verseAbbrIterator = verseAbbrJsonObject.keys();
        while(verseAbbrIterator.hasNext()) {
            String key = verseAbbrIterator.next();
            abbrevToLongMap.put(verseAbbrJsonObject.get(key).toString(), key);
            longToAbbrevMap.put(key, verseAbbrJsonObject.get(key).toString());
        }

        System.out.println("Here is your map"  + abbrevToLongMap + "----" + longToAbbrevMap);
    }

    public List<String> getAllBooks() {
        return booksInOrder;
    }

    public List<String> getAllChaptersOfABook(String book) {
        System.out.println("The keys of the bible data are"  + bibleData.keySet());
        List<String> allChapters = new ArrayList<>(bibleData.get(book).keySet());
        Collections.sort(allChapters, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        });

        return allChapters;
    }

    public List<Verse> getAllVersesOfAChapter(String book, String chapter) {
        Map<String, String> chapterInfo = bibleData.get(book).get(chapter);
        List<Verse> verses = new ArrayList<>();

        for(String verseNumber: chapterInfo.keySet()) {
            verses.add(new Verse(verseNumber, chapterInfo.get(verseNumber)));
        }

        Collections.sort(verses, new Comparator<Verse>() {
            @Override
            public int compare(Verse o1, Verse o2) {
                return Integer.parseInt(o1.getVerseNumber()) - Integer.parseInt(o2.getVerseNumber());
            }
        });

        return verses;
    }

    public static BibleReader getSingleton(Context context) throws IOException, JSONException {
        if (SINGLETON  == null) {
            SINGLETON = new BibleReader(context);
        }

        return SINGLETON;
    }

    public static String getLongVersionBookName(String shortVersionBookName) {
        // Change incoming string to lower case first to comply with data format.
        return SINGLETON.abbrevToLongMap.get(shortVersionBookName.toLowerCase());
    }

    public static String getShortVersionBookName(String longVersionBookName) {
        // Change incoming string to lower case first to comply with data format.
        return SINGLETON.longToAbbrevMap.get(longVersionBookName);
    }


}
