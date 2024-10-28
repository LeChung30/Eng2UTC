package com.example.eng2utc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.TestFragment.AudioManager;
import com.example.eng2utc.TestFragment.TestListeningFragment;

import java.util.ArrayList;

public class TestExerciseActivity extends AppCompatActivity {

    private ArrayList<String> listURL;
    private AudioManager audioManager;
    private ProgressBar progressBar;
    private LinearLayout sidebarLayout;
    private TextView timerTextView;
    private ImageButton btnSidebar, btnCloseSidebar;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60 * 60 * 1000;

    private int currentTestIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exercise);

        Init();

        // Nhận test_name và test_id từ Intent
        int testId = getIntent().getIntExtra("test_id", -1); // -1 là giá trị mặc định nếu không tìm thấy

        // Khởi tạo danh sách URL
        listURL = new ArrayList<>();
        listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-1.mp3?_=1");
        listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-3.mp3?_=1");
        listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-4.mp3?_=1");
        listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-02-part-1.mp3?_=1");

        // Hiển thị ProgressBar khi đang tải
        progressBar.setVisibility(View.VISIBLE);

        // Khởi tạo AudioManager và load fragment
        new Thread(() -> {
            audioManager = new AudioManager(listURL);
            runOnUiThread(() -> {
                loadCurrentFragment(); // Load fragment đầu tiên
                progressBar.setVisibility(View.GONE); // Ẩn ProgressBar
                startTimer(); // Bắt đầu bộ đếm thời gian
            });
        }).start();

        // Thiết lập sự kiện cho sidebarButton
        btnSidebar.setOnClickListener(v -> {
            if (sidebarLayout.getVisibility() == View.GONE) {
                sidebarLayout.setVisibility(View.VISIBLE); // Hiện sidebar
            } else {
                sidebarLayout.setVisibility(View.GONE); // Ẩn sidebar
            }
        });

        btnCloseSidebar.setOnClickListener(v -> {
            sidebarLayout.setVisibility(View.GONE); // Ẩn sidebar khi nhấn nút đóng
        });
    }

    private void Init() {
        progressBar = findViewById(R.id.progressBar);
        sidebarLayout = findViewById(R.id.sidebar_layout);
        btnCloseSidebar = findViewById(R.id.btn_close_sidebar);
        btnSidebar = findViewById(R.id.btn_sidebar);
        timerTextView = findViewById(R.id.timerTextView); // Khởi tạo TextView timer
    }

    private void loadCurrentFragment() {
        if (currentTestIndex < audioManager.getAudioListSize()) {
            MediaPlayer mediaPlayer = audioManager.getMediaPlayer(currentTestIndex);
            TestListeningFragment fragment = TestListeningFragment.newInstance(mediaPlayer);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void loadNextFragment() {
        currentTestIndex++;
        if (currentTestIndex < audioManager.getAudioListSize()) {
            loadCurrentFragment();
        } else {
            currentTestIndex = 0; // Reset về đầu danh sách
            loadCurrentFragment();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) { // Đếm ngược mỗi giây
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished); // Cập nhật giao diện
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00"); // Khi kết thúc, hiển thị 00:00
                // Có thể thêm hành động khi hết thời gian
            }
        }.start(); // Bắt đầu bộ đếm
    }

    private void updateTimer(long millis) {
        int minutes = (int) (millis / 1000) / 60; // Tính số phút
        int seconds = (int) (millis / 1000) % 60; // Tính số giây

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted); // Cập nhật TextView
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioManager != null) {
            audioManager.releasePlayers(); // Giải phóng tài nguyên
        }
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Hủy bộ đếm khi activity bị hủy
        }
    }
}
