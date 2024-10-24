package com.example.eng2utc.Activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.Fragments.LevelBaseFragment;
import com.example.eng2utc.R;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String examTitle = getIntent().getStringExtra("examTitle"); // Nhận dữ liệu từ Intent
       // TextView titleTextView = findViewById(R.id.examTitleTextView);
      //  titleTextView.setText(examTitle); // Hiển thị tiêu đề bài kiểm tra
        FragmentManager fragmentManager = getSupportFragmentManager();
        LevelBaseFragment levelBaseFragment = new LevelBaseFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, levelBaseFragment);
        fragmentTransaction.commit();
    }
}