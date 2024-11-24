package com.example.eng2utc.Dictionary;

import java.util.ArrayList;

public class WordItem {
    private String word = "";
    private ArrayList<Meaning> meanings=null;
    private ArrayList<Phonetics> phonetics =null;
    // private List<String> sourceUrls;


    public WordItem() {
    }

    public WordItem(String word, ArrayList<Meaning> meanings, ArrayList<Phonetics> phonetic) {
        this.word = word;
        this.meanings = meanings;
        this.phonetics = phonetic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meaning> meanings) {
        this.meanings = meanings;
    }

    public ArrayList<Phonetics> getPhonetic() {
        return phonetics;
    }
    public ArrayList<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetic(ArrayList<Phonetics> phonetic) {
        this.phonetics = phonetic;
    }
}
