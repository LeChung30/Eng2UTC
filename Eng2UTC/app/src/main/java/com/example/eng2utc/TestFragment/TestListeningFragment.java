package com.example.eng2utc.TestFragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eng2utc.R;

import java.util.Locale;

public class TestListeningFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;
    private TextView currentTimeTextView, totalTimeTextView;
    private Handler handler = new Handler();

    public TestListeningFragment() {
        // Required empty public constructor
    }

    public static TestListeningFragment newInstance(MediaPlayer mediaPlayer) {
        TestListeningFragment fragment = new TestListeningFragment();
        fragment.setMediaPlayer(mediaPlayer); // Gán mediaPlayer vào fragment
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

        System.out.println("oncreateView");
        // Kiểm tra nếu mediaPlayer không null để thiết lập
        if (mediaPlayer != null) {
            setupMediaPlayer();
        } else {
            // Nếu mediaPlayer là null, đảm bảo button có icon play
            playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
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
