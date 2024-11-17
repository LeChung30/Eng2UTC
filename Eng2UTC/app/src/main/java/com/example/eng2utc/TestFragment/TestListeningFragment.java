package com.example.eng2utc.TestFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eng2utc.Model.Answer;
import com.example.eng2utc.Model.Question;
import com.example.eng2utc.R;
import com.example.eng2utc.TestExerciseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class TestListeningFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;
    private TextView currentTimeTextView, totalTimeTextView;
    private LinearLayout questionContainer;
    private Handler handler = new Handler();
    private List<Question> questions;

    public TestListeningFragment() {
        // Required empty public constructor
    }

    public static TestListeningFragment newInstance(MediaPlayer mediaPlayer, List<Question> questions) {
        TestListeningFragment fragment = new TestListeningFragment();
        fragment.setMediaPlayer(mediaPlayer); // Gán mediaPlayer vào fragment
        fragment.questions = questions;
        return fragment;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_listening, container, false);
        playButton = view.findViewById(R.id.playButton);
        seekBar = view.findViewById(R.id.seekBar);
        currentTimeTextView = view.findViewById(R.id.currentTimeTextView);
        totalTimeTextView = view.findViewById(R.id.totalTimeTextView);
        questionContainer = view.findViewById(R.id.questionContainer);

        // Kiểm tra nếu mediaPlayer không null để thiết lập
        if (mediaPlayer != null) {
            setupMediaPlayer();
        } else {
            // Nếu mediaPlayer là null, đảm bảo button có icon play
            playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
        }

        // Thêm các câu hỏi vào questionContainer
        for (Question question : questions) {
            View questionView = inflater.inflate(R.layout.question_layout, questionContainer, false);
            TextView questionTextView = questionView.findViewById(R.id.tv_question);
            ImageView questionImageView = questionView.findViewById(R.id.img_question);
            RadioGroup questionRG = questionView.findViewById(R.id.gr_btn_question);

            questionTextView.setText(question.getOrder() + "." + question.getContent());
            if (question.getImageLink() != null) {
                Picasso.get().load(question.getImageLink()).into(questionImageView);
            } else {
                questionImageView.setVisibility(View.GONE);
            }

            for (Answer answer : question.getAnswers()) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(answer.getContent());
                radioButton.setOnClickListener(v -> {
                    ((TestExerciseActivity) getActivity()).getAnsweredQuestions().put(question, true);
                    ((TestExerciseActivity) getActivity()).updateSidebarButtonColor(question);
                });
                questionRG.addView(radioButton);
            }

            questionContainer.addView(questionView);
        }

        return view;
    }

    private void setupMediaPlayer() {
        totalTimeTextView.setText(formatDuration(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());

        // Thiết lập lắng nghe sự thay đổi SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress); // Di chuyển đến vị trí được chọn
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateSeekBar); // Dừng cập nhật khi người dùng kéo
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.postDelayed(updateSeekBar, 1000); // Tiếp tục cập nhật sau khi dừng kéo
            }
        });

        // Xử lý sự kiện nhấn nút play
        playButton.setOnClickListener(v -> {
            if (mediaPlayer == null) return; // Kiểm tra xem mediaPlayer có phải là null

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
            } else {
                mediaPlayer.start();
                playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24);
                handler.postDelayed(updateSeekBar, 1000); // Bắt đầu cập nhật SeekBar
            }
        });

        // Cập nhật UI nếu mediaPlayer đang phát
        updatePlayButtonIcon();
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(updateSeekBar, 1000);
        }
    }

    private void updatePlayButtonIcon() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24);
            } else {
                playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
            }
        }
    }

    private String formatDuration(int duration) {
        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                currentTimeTextView.setText(formatDuration(currentPosition));
                handler.postDelayed(this, 1000); // Cập nhật mỗi giây
            }
        }
    };

    public void updateUI(MediaPlayer mediaPlayer) {
        totalTimeTextView.setText(formatDuration(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());

        // Nếu mediaPlayer đang phát, cập nhật button và start seekbar
        if (mediaPlayer.isPlaying()) {
            playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24); // Icon tạm dừng
            handler.postDelayed(updateSeekBar, 1000); // Bắt đầu cập nhật SeekBar
        } else {
            playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24); // Icon phát
        }
    }

    public void resetAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0); // Reset audio về đầu
            mediaPlayer.pause(); // Dừng audio
            updatePlayButtonIcon(); // Cập nhật icon
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Start");
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
            currentTimeTextView.setText(formatDuration(mediaPlayer.getCurrentPosition()));
            if (mediaPlayer.isPlaying()) {
                handler.postDelayed(updateSeekBar, 1000);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("pause fragment");
        resetAudio();
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null; // Đặt lại để tránh lỗi
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            totalTimeTextView.setText(formatDuration(mediaPlayer.getDuration()));
            currentTimeTextView.setText(formatDuration(mediaPlayer.getCurrentPosition()));
            seekBar.setMax(mediaPlayer.getDuration());

            // Nếu đang phát, bắt đầu cập nhật SeekBar
            if (mediaPlayer.isPlaying()) {
                handler.postDelayed(updateSeekBar, 1000);
            }
        }
    }
}
