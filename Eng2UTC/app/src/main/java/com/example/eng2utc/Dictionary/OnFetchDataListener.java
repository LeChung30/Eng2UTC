package com.example.eng2utc.Dictionary;

public interface OnFetchDataListener {
    void onFetchData(WordItem wordItem, String message);
    void onError(String message);

}
