package com.example.eng2utc.Dictionary;

import java.util.ArrayList;

public class Meaning {
    private ArrayList<Definition> definitions;
    private String partOfSpeech="";
    //private List<String> synonyms;

    // Constructor
    public Meaning(ArrayList<Definition> definitions, String partOfSpeech) {
        this.definitions = definitions;
        this.partOfSpeech = partOfSpeech;

    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }


}
