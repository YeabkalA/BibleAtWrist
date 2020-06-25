package com.yeabkalwubshit.watchbible;

public class GenericTableCellData {
    private String text;

    public GenericTableCellData(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
