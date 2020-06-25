package com.yeabkalwubshit.watchbible;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DailyVerseGrabber {
    private static final String BIBLE_VERSE_API_CALL_STR = "https://labs.bible.org/api/?passage=votd&type=json";

    public static VerseOfTheDay loadDailyVerseStringFromAPI() throws Exception{
        URL obj = new URL(BIBLE_VERSE_API_CALL_STR);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        InputStream inputStream;
        StringBuffer response = new StringBuffer();

        try {
            con.setRequestMethod("GET");
            inputStream = new BufferedInputStream(con.getInputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch(Exception e) {
            System.err.println("Network error" + e.toString());
        } finally {
            con.disconnect();
        }

        return new VerseOfTheDay(new JSONArray(response.toString()));
    }

}

class VerseOfTheDay {
    String[][] backingArray;
    String header;
    String verseText;

    public VerseOfTheDay(JSONArray jsonArray) throws JSONException  {
        backingArray = new String[jsonArray.length()][4];
        String[] keys = {"bookname", "chapter", "verse", "text"};
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
            for (int j = 0; j < 4; j++) {
                backingArray[i][j] = jsonObject.get(keys[j]).toString();
            }
        }

        String[] displayableText = getDisplayableText();
        header = displayableText[0];
        verseText = displayableText[1];
    }

    private String[] getDisplayableText() {
        StringBuilder header = new StringBuilder();

        header.append(backingArray[0][0]);
        header.append(" ");
        header.append(backingArray[0][1]);
        header.append(":");

        boolean oneVerse = false;
        if (backingArray[0][2].equals(backingArray[backingArray.length - 1][2])) {
            oneVerse = true;
        }

        header.append(backingArray[0][2]);
        if (!oneVerse) {
            header.append("-" + backingArray[backingArray.length - 1][2]);
        }

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < backingArray.length; i++) {
            text.append(backingArray[i][3] + " ");
        }

        return new String[] {header.toString().trim(), text.toString().trim()};
    }
}

