package com.yeabkalwubshit.watchbible;

public class Verse {
    static final String INTENT_HEADER_EXTRA_KEY = "INTENT_HEADER_EXTRA_KEY";
    static final String INTENT_TEXT_EXTRA_KEY = "INTENT_TEXT_EXTRA_KEY";

    String book;
    String chapter;
    String verseNumber;
    String verse;

    public Verse() {

    }

    public String getHeader() {
        return book + " " + chapter + ":" + verseNumber;
    }

    public Verse(String verseNumber, String verse) {
        this.verseNumber = verseNumber;
        this.verse = verse.replaceAll("~", "");
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public void setVerseNumber(String verseNumber) { this.verseNumber = verseNumber; }

    public void setVerse(String verse) { this.verse = verse; }

    public String getBook() {
        return book;
    }

    public String getChapter() {
        return chapter;
    }

    public String getVerseNumber() {
        return verseNumber;
    }

    public String getVerse() {
        return verse;
    }

    public String serialize() {
        return book + ":" + chapter + ":" + verseNumber;
    }
}
