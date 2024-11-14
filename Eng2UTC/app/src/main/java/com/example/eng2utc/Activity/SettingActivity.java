package com.example.eng2utc.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.eng2utc.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class SettingActivity extends BaseActivity {

    private ImageView btn_back;
    private TextView btnSignOut;
    private SwitchCompat switch_language;
    private SwitchCompat switch_theme;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_back = findViewById(R.id.btn_back_setting);
        btn_back.setOnClickListener(v -> finish());

        btnSignOut = findViewById(R.id.btn_setting_sign_out);
        btnSignOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SettingActivity.this, LogInActivity.class));
        });

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Initialize and set switch language state
        switch_language = findViewById(R.id.switch_language);
        String languageCode = sharedPreferences.getString("language_code", "en");
        switch_language.setChecked(languageCode.equals("vi"));

        switch_language.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeLanguage("vi");
            } else {
                changeLanguage("en");
            }
        });

        // Initialize and set switch theme state
        switch_theme = findViewById(R.id.switch_theme);
        nightMode = sharedPreferences.getBoolean("night", false);
        switch_theme.setChecked(nightMode);

        switch_theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night", true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night", false);
            }
            editor.apply();
        });

        LinearLayout layout_rate = findViewById(R.id.layout_rate);
        layout_rate.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, RateActivity.class);
            startActivity(intent);
        });
    }

    // Function to change language and restart activity
    public void changeLanguage(String newLanguageCode) {
        String currentLanguageCode = sharedPreferences.getString("language_code", "en");
        if (!currentLanguageCode.equals(newLanguageCode)) {
            editor.putString("language_code", newLanguageCode).apply();
            // Restarting the entire application to apply language change globally
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}