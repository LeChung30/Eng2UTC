package com.example.eng2utc.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SwitchCompat;

import com.example.eng2utc.R;

import java.util.Locale;

public class SettingActivity extends BaseActivity {

    private ImageView btn_back;
    private SwitchCompat switch_language;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // Replace with your settings layout

        // Find the ImageView for the back button
        btn_back = findViewById(R.id.btn_back_setting);
        btn_back.setOnClickListener(v -> {
            // Handle the back button click event
            finish(); // Close the activity
        });

        switch_language = findViewById(R.id.switch_language);
        switch_language.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setLocale("vi"); // Chuyển sang tiếng Việt
            } else {
                setLocale("en"); // Chuyển sang tiếng Anh
            }
        });

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

    // Hàm thay đổi ngôn ngữ
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Lưu ngôn ngữ vào SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Language", lang);
        editor.apply();

        // Restart activity to apply changes
        Intent refresh = new Intent(this, SettingActivity.class);
        finish(); // End current activity
        startActivity(refresh); // Restart the activity
    }
}