package com.example.eng2utc.TestFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.eng2utc.Model.Question;
import com.example.eng2utc.TestExerciseActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestRewriteFragment extends Fragment {

    private List<Question> questions;

    public TestRewriteFragment() {
        // Required empty public constructor
    }

    public static TestRewriteFragment newInstance(List<Question> questions) {
        TestRewriteFragment fragment = new TestRewriteFragment();
        fragment.questions = questions;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scrollView = new ScrollView(getContext());
        LinearLayout questionContainer = new LinearLayout(getContext());
        questionContainer.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(questionContainer);

        if (questions != null) {
            for (Question question : questions) {
                LinearLayout questionLayout = createQuestionLayout(question);
                questionContainer.addView(questionLayout);
            }
        }

        return scrollView;
    }

    private LinearLayout createQuestionLayout(Question question) {
        LinearLayout questionLayout = new LinearLayout(getContext());
        questionLayout.setOrientation(LinearLayout.VERTICAL);
        questionLayout.setPadding(16, 16, 16, 16);

        TextView tvQuestion = new TextView(getContext());
        tvQuestion.setText(question.getOrder() + ".");
        tvQuestion.setTextSize(16);
        tvQuestion.setPadding(0, 0, 0, 8);
        questionLayout.addView(tvQuestion);

        FlexboxLayout layoutWordsToArrange = new FlexboxLayout(getContext());
        layoutWordsToArrange.setFlexWrap(FlexWrap.WRAP);
        layoutWordsToArrange.setJustifyContent(JustifyContent.CENTER);
        layoutWordsToArrange.setPadding(8, 8, 8, 8);
        layoutWordsToArrange.setBackgroundColor(Color.LTGRAY);
        layoutWordsToArrange.setLayoutParams(createLayoutParams(8));
        questionLayout.addView(layoutWordsToArrange);

        FlexboxLayout layoutResult = new FlexboxLayout(getContext());
        layoutResult.setFlexWrap(FlexWrap.WRAP);
        layoutResult.setJustifyContent(JustifyContent.CENTER);
        layoutResult.setPadding(8, 8, 8, 8);
        layoutResult.setBackgroundColor(Color.LTGRAY);
        layoutResult.setLayoutParams(createLayoutParams(8));
        questionLayout.addView(layoutResult);

        String[] words = question.getContent().split("/");
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, words);
        Collections.shuffle(wordList);

        for (String word : wordList) {
            Button wordButton = createWordButton(word, layoutWordsToArrange, layoutResult, question);
            layoutWordsToArrange.addView(wordButton);
        }

        return questionLayout;
    }

    private Button createWordButton(String word, FlexboxLayout layoutWordsToArrange, FlexboxLayout layoutResult, Question question) {
        Button button = new Button(getContext());
        button.setText(word);
        button.setAllCaps(false);
        button.setTextSize(14);
        button.setPadding(8, 8, 8, 8);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);

        button.setOnClickListener(v -> {
            moveToResultLayout(button, layoutWordsToArrange, layoutResult, question);
        });

        return button;
    }

    private void moveToResultLayout(Button button, FlexboxLayout layoutWordsToArrange, FlexboxLayout layoutResult, Question question) {
        layoutWordsToArrange.removeView(button);
        layoutResult.addView(button);
        button.setOnClickListener(v -> moveToWordsToArrangeLayout(button, layoutWordsToArrange, layoutResult, question));
        updateAnsweredQuestions(layoutResult, question);
    }

    private void moveToWordsToArrangeLayout(Button button, FlexboxLayout layoutWordsToArrange, FlexboxLayout layoutResult, Question question) {
        layoutResult.removeView(button);
        layoutWordsToArrange.addView(button);
        button.setOnClickListener(v -> moveToResultLayout(button, layoutWordsToArrange, layoutResult, question));
        updateAnsweredQuestions(layoutResult, question);
    }

    private void updateAnsweredQuestions(FlexboxLayout layoutResult, Question question) {
        StringBuilder answerBuilder = new StringBuilder();
        int childCount = layoutResult.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Button wordButton = (Button) layoutResult.getChildAt(i);
            String word = wordButton.getText().toString().trim();
            if (word.equals(".") || word.equals(",") || word.equals("!") || word.equals("?")) {
                // Append punctuation directly without space
                if (answerBuilder.length() > 0) {
                    answerBuilder.setLength(answerBuilder.length() - 1); // Remove trailing space
                }
                answerBuilder.append(word).append(" ");
            } else {
                answerBuilder.append(word).append(" ");
            }
        }

        String answer = answerBuilder.toString().trim();
        ((TestExerciseActivity) getActivity()).getAnsweredQuestions().put(question, answer);
        ((TestExerciseActivity) getActivity()).updateSidebarButtonColor(question);
    }

    private LinearLayout.LayoutParams createLayoutParams(int margin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, margin, margin, margin);
        return params;
    }
}