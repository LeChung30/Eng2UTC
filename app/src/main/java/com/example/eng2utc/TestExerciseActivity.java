package com.example.eng2utc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.TestFragment.AudioManager;
import com.example.eng2utc.TestFragment.TestListeningFragment;

import java.util.ArrayList;

public class TestExerciseActivity extends AppCompatActivity {

    private Button btnPlay, btnNext;
    private ArrayList<String> listURL;
    private AudioManager audioManager;
    private ProgressBar progressBar;
    private int currentTestIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            btnPlay = findViewById(R.id.btnPlay);
            btnNext = findViewById(R.id.btnNext);
            progressBar = findViewById(R.id.progressBar);

            listURL = new ArrayList<>();
            listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-1.mp3?_=1");
            listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-3.mp3?_=1");
            listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-01-part-4.mp3?_=1");
            listURL.add("https://englishpracticetest.net/wp-content/uploads/2021/11/ket-practice-listening-test-02-part-1.mp3?_=1");

            // Sự kiện cho nút Play
            btnPlay.setOnClickListener(view -> {
                progressBar.setVisibility(View.VISIBLE);

                // Giả lập thời gian loading
                new Thread(() -> {
                    audioManager = new AudioManager(listURL);

                    runOnUiThread(() -> {
                        loadCurrentFragment();
                        progressBar.setVisibility(View.GONE); // Ẩn ProgressBar
                    });
                }).start();
            });

            // Sự kiện cho nút Next
            btnNext.setOnClickListener(view -> loadNextFragment());

            return insets;
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioManager != null) {
            audioManager.releasePlayers(); // Giải phóng tài nguyên
        }
    }
}
