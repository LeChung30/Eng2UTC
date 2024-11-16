package com.example.eng2utc.TestFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.eng2utc.Model.Answer;
import com.example.eng2utc.Model.PartDetail;
import com.example.eng2utc.Model.Question;
import com.example.eng2utc.R;
import com.example.eng2utc.TestExerciseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TestMultipleFragment extends Fragment {

    private List<Question> questions;

    public TestMultipleFragment() {
        // Required empty public constructor
    }

    public static TestMultipleFragment newInstance(List<Question> questions) {
        TestMultipleFragment fragment = new TestMultipleFragment();
        fragment.questions = questions;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_multiple, container, false);

        LinearLayout questionContainer = view.findViewById(R.id.questionContainer);

        if (questions != null) {
            for (Question question : questions) {
                View questionView = inflater.inflate(R.layout.question_layout, questionContainer, false);

                TextView tvQuestion = questionView.findViewById(R.id.tv_question);
                ImageView imgQuestion = questionView.findViewById(R.id.img_question);
                RadioGroup rgAnswers = questionView.findViewById(R.id.gr_btn_question);

                tvQuestion.setText(question.getOrder() + ". " + question.getContent());

                if (question.getImageLink() != null) {
                    imgQuestion.setVisibility(View.VISIBLE);
                    Picasso.get().load(question.getImageLink()).into(imgQuestion);
                } else {
                    imgQuestion.setVisibility(View.GONE);
                }

                for (Answer answer : question.getAnswers()) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(answer.getContent());
                    radioButton.setOnClickListener(v -> {
                        ((TestExerciseActivity) getActivity()).getAnsweredQuestions().put(question, true);
                        ((TestExerciseActivity) getActivity()).updateSidebarButtonColor(question);
                    });
                    rgAnswers.addView(radioButton);
                }

                questionContainer.addView(questionView);
            }
        }

        return view;
    }
}
