package com.example.eng2utc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eng2utc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public String TAG = "Eng2UTC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLocale();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    public void applyLocale() {
        String languageCode = getLanguageCodeFromPreferences();
        setLocale(languageCode);
    }

    private String getLanguageCodeFromPreferences() {
        return getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getString("language_code", "en"); // Ngôn ngữ mặc định là "en"
    }

    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void changeLanguage(String newLanguageCode) {
        String currentLanguageCode = getLanguageCodeFromPreferences();
        if (!currentLanguageCode.equals(newLanguageCode)) { // Kiểm tra nếu ngôn ngữ khác
            // Lưu ngôn ngữ mới vào SharedPreferences
            getSharedPreferences("settings", Context.MODE_PRIVATE)
                    .edit().putString("language_code", newLanguageCode).apply();
            // Khởi động lại Activity
            Intent intent = new Intent(this, getClass());
            finish();
            startActivity(intent);
        }
    }
}