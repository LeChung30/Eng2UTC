package com.example.eng2utc.TestFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eng2utc.R;

public class ResultTestFragment extends DialogFragment {

    private int score;
    private int totalQuestions;
    private String timeTaken;
    private String startTime, duration;

    public static ResultTestFragment newInstance(int score, int totalQuestions, String timeTaken, String duration, String startTime) {
        ResultTestFragment fragment = new ResultTestFragment();
        fragment.score = score;
        fragment.totalQuestions = totalQuestions;
        fragment.timeTaken = timeTaken;
        fragment.duration = duration;
        fragment.startTime = startTime;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_test, container, false);

        if (getArguments() != null) {
            score = getArguments().getInt("score");
            totalQuestions = getArguments().getInt("totalQuestions");
            timeTaken = getArguments().getString("timeTaken");
            startTime = getArguments().getString("startTime");
        }

        TextView scoreTextView = view.findViewById(R.id.scoreTextView);
        TextView timeTakenTextView = view.findViewById(R.id.timeTakenTextView);
        TextView currentTimeTextView = view.findViewById(R.id.currentTimeTextView);
        TextView reviewButton = view.findViewById(R.id.reviewButton);
        TextView closeButton = view.findViewById(R.id.closeButton);

        scoreTextView.setText("Điểm: " + score + "/" + totalQuestions);
        timeTakenTextView.setText("Thời gian làm bài: " + timeTaken + "/" + duration + ":00");
        currentTimeTextView.setText("Thời gian thực hiện: " + startTime);

        reviewButton.setOnClickListener(v -> {
            // Handle review button click
        });

        closeButton.setOnClickListener(v -> {
            // Handle close button click
            getActivity().finish();
        });

        return view;
    }
}