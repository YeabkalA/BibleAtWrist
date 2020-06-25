package com.yeabkalwubshit.watchbible;

public class GenericTableCellData {
    private String text;
    private Verse verse;

    public GenericTableCellData(String text) {
        this.text = text;
    }

    public void setVerse(Verse verse) {
        this.verse = verse;
    }

    public Verse getVerse() {
        return verse;
    }

    @Override
    public String toString() {
        return text;
    }
}
