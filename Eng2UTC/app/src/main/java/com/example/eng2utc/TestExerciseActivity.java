package com.example.eng2utc;

import static androidx.core.util.TimeUtils.formatDuration;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Fragments.ExamForLevelFragment;
import com.example.eng2utc.Model.Answer;
import com.example.eng2utc.Model.PartDetail;
import com.example.eng2utc.Model.PartDetailResponse;
import com.example.eng2utc.Model.Question;
import com.example.eng2utc.Model.UserAnswer;
import com.example.eng2utc.Model.UserTest;
import com.example.eng2utc.Retrofit.RetrofitController;
import com.example.eng2utc.TestFragment.ResultTestFragment;
import com.example.eng2utc.TestFragment.TestMultipleFragment;
import com.example.eng2utc.TestFragment.TestReadingFragment;
import com.example.eng2utc.TestFragment.TestListeningFragment;
import com.example.eng2utc.TestFragment.TestRewriteFragment;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class TestExerciseActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout sidebarLayout;
    private TextView timerTextView;
    private ImageButton btnSidebar, btnCloseSidebar;
    private TextView btnSubmit;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60 * 60 * 1000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private FirebaseController firebaseController = new FirebaseController();

    private List<PartDetail> partDetails = new ArrayList<>();
    private List<MediaPlayer> mediaPlayers = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentTestIndex = 0, questionCount = 0;
    private String testId;
    private Map<Question, String> answeredQuestions = new HashMap();

    public Map<Question, String> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(Map<Question, String> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exercise);

        initializeViews();
        testId = getIntent().getStringExtra("test_id");
        boolean reviewMode = getIntent().getBooleanExtra("review_mode", false);

        if (reviewMode) {
            btnSubmit.setEnabled(false); // Disable the submit button
            String userTestId = getIntent().getStringExtra("user_test_id");
            fetchUserAnswers(userTestId);
        } else {
            fetchTestData(testId);
        }
        setupSidebarButtonListeners();
    }

    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar);
        sidebarLayout = findViewById(R.id.sidebar_layout);
        btnCloseSidebar = findViewById(R.id.btn_close_sidebar);
        btnSidebar = findViewById(R.id.btn_sidebar);
        btnSubmit = findViewById(R.id.btn_submit_test);
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
        this.questionCount += questionCount;
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
                            if (!answeredQuestions.getOrDefault(question, "").isEmpty()) {
                                questionButton.setBackgroundColor(Color.GREEN); // Change to desired color
                            } else {
                                questionButton.setBackgroundColor(Color.YELLOW); // Change to desired color
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
                submitTest();
            }
        }.start();
    }

    private void updateTimerUI(long millisUntilFinished) {
        timeLeftInMillis = millisUntilFinished;
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
        btnSubmit.setOnClickListener(v -> submitTest());
    }

    private void submitTest() {
        countDownTimer.cancel();
        sidebarLayout.setVisibility(View.GONE);

        // Tính điểm
        int totalScore = 0;
        for (Map.Entry<Question, String> entry : answeredQuestions.entrySet()) {
            Question question = entry.getKey();
            String answer = entry.getValue();
            if(question.getCorrectAnswerId().equals(answer) ||
                question.getAnswers().get(0).getContent().toLowerCase().equals(answer)) {
                totalScore++;
            }
        }
        // Thời gian làm bài ngày giờ hiện tại lúc ấn
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String startTime = sdf.format(new Date());
        String curTime = formatDuration(START_TIME_IN_MILLIS - timeLeftInMillis);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "Unknown User");

        // Lưu kết quả vào firebase
        UserTest userTest = new UserTest(null, userId, testId, startTime, curTime, totalScore);

        // Push the data to Firebase
        firebaseController.addUserTest(userTest, new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // Handle success
                Toast.makeText(TestExerciseActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                // Refresh the data in ExamForLevelFragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                ExamForLevelFragment fragment = (ExamForLevelFragment) fragmentManager.findFragmentByTag("exam_for_level_fragment");
                if (fragment != null) {
                    fragment.refreshData();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure
                Toast.makeText(TestExerciseActivity.this, "Failed to add data: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Push user answers to Firebase
        for (Map.Entry<Question, String> entry : answeredQuestions.entrySet()) {
            Question question = entry.getKey();
            String rawUserAnswer = entry.getValue();
            String answerId = question.getCorrectAnswerId(); // Assuming you have the correct answer ID
            String userAnswerId = UUID.randomUUID().toString(); // Generate a unique ID for the user answer
            String userTestId = userTest.getUSER_TEST_ID(); // Assuming you have the user test ID

            UserAnswer userAnswer = new UserAnswer(userAnswerId, userId, testId, question.getQUESTION_ID(), answerId, rawUserAnswer, "", userTestId);
            firebaseController.addUserAnswer(userAnswer, new FirebaseDataCallback() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    // Handle success
                    Toast.makeText(TestExerciseActivity.this, "Answer added successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Handle failure
                    Toast.makeText(TestExerciseActivity.this, "Failed to add answer: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Hiển thị kết quả
        ResultTestFragment resultTestFragment = ResultTestFragment.newInstance(totalScore, questionCount, curTime, "60", startTime);
        resultTestFragment.show(getSupportFragmentManager(), "resultTestDialog");
        Toast.makeText(this, userId + " submit: " + totalScore, Toast.LENGTH_SHORT).show();
    }

    private String formatDuration(long duration) {
        int minutes = (int) (duration / 1000) / 60;
        int seconds = (int) (duration / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void fetchUserAnswers(String userTestId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "Unknown User");

        firebaseController.getData("USER_ANSWER", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserAnswer userAnswer = snapshot.getValue(UserAnswer.class);
                    if (userAnswer != null && userAnswer.getUSER_TEST_ID().equals(userTestId) && userAnswer.getUSER_ID().equals(userId)) {
                        // Find the corresponding question and set the answer
                        for (PartDetail partDetail : partDetails) {
                            for (Question question : partDetail.getQuestions()) {
                                if (question.getQUESTION_ID().equals(userAnswer.getQUESTION_ID())) {
                                    answeredQuestions.put(question, userAnswer.getRAW_USER_ANSWER());
                                    break;
                                }
                            }
                        }
                    }
                }
                fetchTestData(testId); // Load the test data after fetching user answers
                fetchUserTestDuration(userTestId); // Fetch and display the duration
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure
                Toast.makeText(TestExerciseActivity.this, "Failed to fetch user answers: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserTestDuration(String userTestId) {
        firebaseController.getData("USER_TEST", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTest userTest = snapshot.getValue(UserTest.class);
                    if (userTest != null && userTest.getUSER_TEST_ID().equals(userTestId)) {
                        timerTextView.setText(userTest.getDURATION()); // Display the duration
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure
                Toast.makeText(TestExerciseActivity.this, "Failed to fetch user test duration: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
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
