package com.example.eng2utc;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.Model.Answer;
import com.example.eng2utc.Model.PartDetail;
import com.example.eng2utc.Model.PartDetailResponse;
import com.example.eng2utc.Model.Question;
import com.example.eng2utc.Retrofit.RetrofitController;
import com.example.eng2utc.TestFragment.TestMultipleFragment;
import com.example.eng2utc.TestFragment.TestReadingFragment;
import com.example.eng2utc.TestFragment.TestListeningFragment;
import com.example.eng2utc.TestFragment.TestRewriteFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class TestExerciseActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout sidebarLayout;
    private TextView timerTextView;
    private ImageButton btnSidebar, btnCloseSidebar;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60 * 60 * 1000;

    private List<PartDetail> partDetails = new ArrayList<>();
    private List<MediaPlayer> mediaPlayers = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentTestIndex = 0;
    private Map<Question, Boolean> answeredQuestions = new HashMap();

    public Map<Question, Boolean> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(Map<Question, Boolean> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exercise);

        initializeViews();
        String testId = getIntent().getStringExtra("test_id");
        fetchTestData(testId);
        setupSidebarButtonListeners();
    }

    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar);
        sidebarLayout = findViewById(R.id.sidebar_layout);
        btnCloseSidebar = findViewById(R.id.btn_close_sidebar);
        btnSidebar = findViewById(R.id.btn_sidebar);
        timerTextView = findViewById(R.id.timerTextView);
    }

    private void fetchTestData(String testId) {
        progressBar.setVisibility(View.VISIBLE); // Hiển thị progressBar khi bắt đầu tải dữ liệu

        RetrofitController.getApiService().getPartDetails(testId).enqueue(new retrofit2.Callback<PartDetailResponse>() {
            @Override
            public void onResponse(Call<PartDetailResponse> call, retrofit2.Response<PartDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    partDetails = response.body().getPartDetails();

                    if (!partDetails.isEmpty()) {
                        // Tải toàn bộ media (audio và image) cho tất cả các phần
                        loadMedia(() -> {
                            createFragments();

                            // Hiển thị tạm thời tất cả các fragment để tải dữ liệu
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            for (Fragment fragment : fragments) {
                                fragmentTransaction.add(R.id.fragment_container, fragment);
                            }
                            fragmentTransaction.commitNowAllowingStateLoss(); // Commit ngay để các fragment có thể tự tải dữ liệu

                            // Ẩn tất cả các fragment, chỉ hiển thị Part 1
                            FragmentTransaction hideTransaction = getSupportFragmentManager().beginTransaction();
                            for (int i = 0; i < fragments.size(); i++) {
                                if (i == 0) {
                                    hideTransaction.show(fragments.get(i)); // Hiển thị Part 1
                                } else {
                                    hideTransaction.hide(fragments.get(i)); // Ẩn các fragment còn lại
                                }
                            }
                            hideTransaction.commit();
                            loadSidebarData();
                            // Ẩn progressBar và bắt đầu đếm ngược
                            progressBar.setVisibility(View.GONE);
                            startTimer();
                        });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.e("TestExerciseActivity", "No part details received.");
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("TestExerciseActivity", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<PartDetailResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("TestExerciseActivity", "Error fetching test data: " + t.getMessage());
            }
        });
    }

    private void loadSidebarData() {
        for (PartDetail partDetail : partDetails) {
            createPartLayout(partDetail);
        }
    }

    private void createPartLayout(PartDetail partDetail) {
        LinearLayout partLayout = new LinearLayout(this);
        partLayout.setOrientation(LinearLayout.VERTICAL);
        partLayout.setPadding(16, 0, 16, 16);

        TextView partTitle = new TextView(this);
        partTitle.setText("Part " + partDetail.getOrder());
        partTitle.setTextSize(20);
        partLayout.addView(partTitle);

        partLayout.setOnClickListener(v -> showPartDetailFragment(partDetail));

        LinearLayout questionLayout = new LinearLayout(this);
        createQuestionButtons(partDetail, questionLayout);

        partLayout.addView(questionLayout);
        sidebarLayout.addView(partLayout);
    }

    private void createQuestionButtons(PartDetail partDetail, LinearLayout questionLayout) {
        List<Question> questions = partDetail.getQuestions();
        int questionCount = questions.size();
        for (int i = 0; i < questionCount; i++) {

            Button questionButton = new Button(this);
            questionButton.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            questionButton.setText(questions.get(i).getOrder() + "");
            questionButton.setTextSize(20);
            questionButton.setBackgroundResource(R.color.white);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) questionButton.getLayoutParams();
            layoutParams.setMargins(16, 16, 16, 16);
            questionButton.setLayoutParams(layoutParams);
            questionButton.setOnClickListener(v -> showPartDetailFragment(partDetail));
            questionLayout.addView(questionButton);
        }
    }

    public void updateSidebarButtonColor(Question question) {
        int partIndex = -1;
        int questionIndex = -1;

        // Find the part and question index
        for (int i = 0; i < partDetails.size(); i++) {
            PartDetail partDetail = partDetails.get(i);
            if (partDetail.getQuestions().contains(question)) {
                partIndex = i;
                questionIndex = partDetail.getQuestions().indexOf(question);
                break;
            }
        }

        if (partIndex != -1 && questionIndex != -1) {
            View partView = sidebarLayout.getChildAt(partIndex+1);
            if (partView instanceof LinearLayout) {
                LinearLayout partLayout = (LinearLayout) partView;
                if (partLayout.getChildCount() > 1) {
                    View questionView = partLayout.getChildAt(1);
                    if (questionView instanceof LinearLayout) {
                        LinearLayout questionLayout = (LinearLayout) questionView;
                        View buttonView = questionLayout.getChildAt(questionIndex);
                        if (buttonView instanceof Button) {
                            Button questionButton = (Button) buttonView;

                            // Update button color based on whether the question is answered
                            if (answeredQuestions.getOrDefault(question, false)) {
                                questionButton.setBackgroundColor(Color.GREEN); // Change to desired color
                            } else {
                                questionButton.setBackgroundColor(Color.RED); // Change to desired color
                            }
                        }
                    }
                }
            }
        }
    }

    private void showPartDetailFragment(PartDetail partDetail) {
        int index = partDetails.indexOf(partDetail);
        System.out.println(index);

        if (index == currentTestIndex) {
            // If the selected part is already displayed, do nothing
            return;
        }

        if (index != -1 && index < fragments.size()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Dừng tất cả các MediaPlayer
            for (MediaPlayer mediaPlayer : mediaPlayers) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0); // Đặt lại về đầu
                }
            }

            // Ẩn tất cả các fragment
            for (Fragment fragment : fragments) {
                fragmentTransaction.hide(fragment);
            }

            // Hiển thị fragment được chọn
            Fragment selectedFragment = fragments.get(index);
            if (!selectedFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, selectedFragment);
            } else {
                fragmentTransaction.show(selectedFragment);
            }

            // Nếu fragment là TestListeningFragment, bắt đầu phát audio tương ứng
            if (selectedFragment instanceof TestListeningFragment && index < mediaPlayers.size()) {
                MediaPlayer mediaPlayer = mediaPlayers.get(index);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    ((TestListeningFragment) selectedFragment).updateUI(mediaPlayer);
                }
            }

            currentTestIndex = index;
            sidebarLayout.setVisibility(View.GONE);
            fragmentTransaction.commit();
        }
    }

    private void onQuestionClick(PartDetail partDetail, int questionIndex) {
        int index = partDetails.indexOf(partDetail);
        if (index != -1 && index < fragments.size()) {
            replaceCurrentFragment(fragments.get(index));
            currentTestIndex = index;
        }
    }

    private void loadMedia(final Runnable onMediaLoaded) {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // Chuyển sang một luồng duy nhất để đảm bảo tuần tự
        List<Runnable> tasks = new ArrayList<>();

        // Tạo từng tác vụ để tải audio và hình ảnh cho mỗi part
        for (PartDetail partDetail : partDetails) {
            tasks.add(() -> {
                preloadAudio(partDetail);
                preloadImages(partDetail);
            });
        }

        // Từng tác vụ sẽ được thực thi tuần tự
        executor.execute(() -> {
            for (Runnable task : tasks) {
                task.run();
            }
            // Khi hoàn thành, gọi lại hàm để load xong toàn bộ media
            runOnUiThread(onMediaLoaded);
        });
    }

    private void preloadAudio(PartDetail partDetail) {
        if (partDetail.getAudioLink() != null) {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(partDetail.getAudioLink());
                mediaPlayer.prepare();
                mediaPlayers.add(mediaPlayer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void preloadImages(PartDetail partDetail) {
        for (Question question : partDetail.getQuestions()) {
            if (question.getImageLink() != null) {
                Picasso.get().load(question.getImageLink()).fetch();
            }
            for (Answer answer : question.getAnswers()) {
                if (answer.getImageLink() != null) {
                    Picasso.get().load(answer.getImageLink()).fetch();
                }
            }
        }
    }

    private void createFragments() {
        for (int i = 0; i < partDetails.size(); i++) {
            PartDetail partDetail = partDetails.get(i);
            Fragment fragment = createFragmentForPartDetail(partDetail, i);
            fragments.add(fragment);
        }
    }

    private Fragment createFragmentForPartDetail(PartDetail partDetail, int index) {
        if (partDetail.getAudioLink() != null) {
            MediaPlayer mediaPlayer = mediaPlayers.get(index);
            return TestListeningFragment.newInstance(mediaPlayer, partDetail.getQuestions());
        } else if (partDetail.getContent() != null) {
            return TestReadingFragment.newInstance(partDetail.getContent(), partDetail.getQuestions());
        } else {
            if (index != 7) {
                return TestMultipleFragment.newInstance(partDetail.getQuestions());
            }
            return TestRewriteFragment.newInstance(partDetail.getQuestions());
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerUI(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
            }
        }.start();
    }

    private void updateTimerUI(long millisUntilFinished) {
        int secondsRemaining = (int) (millisUntilFinished / 1000);
        timerTextView.setText(String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60));
    }

    private void replaceCurrentFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        fragmentTransaction.commit();
    }

    private void setupSidebarButtonListeners() {
        btnSidebar.setOnClickListener(v -> sidebarLayout.setVisibility(View.VISIBLE));
        btnCloseSidebar.setOnClickListener(v -> sidebarLayout.setVisibility(View.GONE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MediaPlayer mediaPlayer : mediaPlayers) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        }
    }
}
