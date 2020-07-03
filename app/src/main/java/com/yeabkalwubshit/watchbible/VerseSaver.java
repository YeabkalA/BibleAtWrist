package com.yeabkalwubshit.watchbible;

import android.content.Context;
import android.content.SharedPreferences;

public class VerseSaver {

    public VerseSaver(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static final String SAVED_VERSES_SHARED_PREF = "SAVED_VERSES_SHARED_PREF";
    public static final String NUM_SAVED_VERSES_SHARED_PREF_KEY =
            "NUM_SAVED_VERSES_SHARED_PREF_KEY";
    public Context applicationContext;
    public static VerseSaver SINGLETON;

    public static String getKeyFromIthVerse(int i) {
        return "key" + i;
    }

    public void saveVerseOnSharedPref(Verse verse) {
        SharedPreferences sharedPreferences =
                applicationContext.getSharedPreferences(SAVED_VERSES_SHARED_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int prevSavedVerses = sharedPreferences.getInt(NUM_SAVED_VERSES_SHARED_PREF_KEY, 0);
        if (sharedPreferences.getBoolean(verse.serialize(), false)) {
            // verse already saved.
            return;
        }

        System.out.println("Pref saved num " + prevSavedVerses);
        for (int i =  1; i <= prevSavedVerses; i++) {
            System.out.println("Showing pref saved " + sharedPreferences.getString(getKeyFromIthVerse(i), "DEF"));
        }

        editor.putString(getKeyFromIthVerse(prevSavedVerses + 1), verse.serialize());
        editor.putBoolean(verse.serialize(), true);
        editor.putInt(NUM_SAVED_VERSES_SHARED_PREF_KEY, prevSavedVerses + 1);
        editor.apply();
    }

    public boolean checkIfVerseSavedOnSharedPref(Verse verse) {
        SharedPreferences sharedPreferences =
                applicationContext.getSharedPreferences(SAVED_VERSES_SHARED_PREF, 0);
        return sharedPreferences.getBoolean(verse.serialize(), false);
    }

    public static void saveVerse(Context context, Verse verse) {
        if (SINGLETON == null) {
            SINGLETON = new VerseSaver(context);
        }

        SINGLETON.saveVerseOnSharedPref(verse);
    }

    public static boolean isVerseSaved(Context context, Verse verse) {
        if (SINGLETON == null) {
            SINGLETON = new VerseSaver(context);
        }

        return SINGLETON.checkIfVerseSavedOnSharedPref(verse);
    }
}
