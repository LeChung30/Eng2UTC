package com.example.eng2utc;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo Firebase
        FirebaseApp.initializeApp(this);
    }
}
