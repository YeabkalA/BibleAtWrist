package com.yeabkalwubshit.watchbible;

public class Verse {
    String book;
    String chapter;
    final String verseNumber;
    final String verse;

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
}
