package com.example.eng2utc.Firebase;

import com.google.firebase.database.DataSnapshot;

public interface FirebaseDataCallback {
    void onSuccess(DataSnapshot dataSnapshot);
    void onFailure(String errorMessage);
}
