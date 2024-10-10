package com.example.eng2utc.TestFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

        // Set total duration
        if (mediaPlayer != null) {
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
        }

        // Xử lý sự kiện nhấn nút play
        playButton.setOnClickListener(v -> {
            if (mediaPlayer == null) return; // Kiểm tra xem mediaPlayer có phải là null

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24);
            } else {
                mediaPlayer.start();
                playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24);
                handler.postDelayed(updateSeekBar, 1000);
            }
        });

        return view;
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

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_24); // Đặt lại hình ảnh nút khi tạm dừng
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null; // Đặt lại để tránh lỗi
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
