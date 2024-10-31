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

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.eng2utc.R;

import java.util.Locale;

public class SettingActivity extends BaseActivity {

    private ImageView btn_back;
    private SwitchCompat switch_language;
    private SwitchCompat switch_theme;
    private SharedPreferences sharedPreferences;
    private boolean nightMode;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // Replace with your settings layout

        // Find the ImageView for the back button
        btn_back = findViewById(R.id.btn_back_setting);
        btn_back.setOnClickListener(v -> {
            finish();
        });
        // Switch language en -> vi
        switch_language = findViewById(R.id.switch_language);
        switch_language.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeLanguage("vi"); // Chuyển sang tiếng Việt nếu khác ngôn ngữ hiện tại
            } else {
                changeLanguage("en"); // Chuyển sang tiếng Anh nếu khác ngôn ngữ hiện tại
            }
        });

        // Switch theme
        switch_theme = findViewById(R.id.switch_theme);
        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false); // light mode is the default mode
        if (nightMode) {
            switch_theme.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switch_theme.setOnClickListener(v -> {
            if (nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("night",false);

            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("night",true);
            }
            editor.apply();
        });

        // Handle layout_rate click
        LinearLayout layout_rate = findViewById(R.id.layout_rate);
        layout_rate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào layout_rate
                Intent intent = new Intent(SettingActivity.this, RateActivity.class);
                startActivity(intent);
            }
        });
    }
}