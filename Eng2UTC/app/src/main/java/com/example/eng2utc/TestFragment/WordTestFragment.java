package com.example.eng2utc.TestFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordTestFragment extends Fragment {

    private static final String ARG_VOCABULARY_LIST = "vocabularyList";
    private static final String ARG_INDEX = "currentWordIndex";

    private List<Vocabulary> vocabularyList;
    private int currentWordIndex;
    private Vocabulary currentVocabulary;

    private LinearLayout layoutLetterAnswer, layoutLetterQuestion;
    private ImageView imgWordTest;
    private Button checkButton;
    private ImageButton btnSpeakerWordTest;
    private List<String> answerLetters = new ArrayList<>();
    private List<String> questionLetters = new ArrayList<>();

    public static WordTestFragment newInstance(List<Vocabulary> vocabularyList, int index) {
        WordTestFragment fragment = new WordTestFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOCABULARY_LIST, (Serializable) vocabularyList);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vocabularyList = (List<Vocabulary>) getArguments().getSerializable(ARG_VOCABULARY_LIST);
            currentWordIndex = getArguments().getInt(ARG_INDEX);
            currentVocabulary = vocabularyList.get(currentWordIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_test, container, false);
        initViews(view);
        setVocabularyData(currentVocabulary);
        checkButton.setOnClickListener(v -> checkAnswer());
        return view;
    }

    private void initViews(View view) {
        layoutLetterAnswer = view.findViewById(R.id.layout_letter_answer);
        layoutLetterQuestion = view.findViewById(R.id.layout_letter_question);
        checkButton = view.findViewById(R.id.checkButton);
        imgWordTest = view.findViewById(R.id.img_word_test_fragment);
        btnSpeakerWordTest = view.findViewById(R.id.btn_speaker_word_test);
    }

    private void setVocabularyData(Vocabulary vocab) {
        loadVocabularyImage(vocab.getIMAGE_LINK());
        setupAudioButton(vocab.getAUDIO_LINK());

        layoutLetterAnswer.removeAllViews();
        layoutLetterQuestion.removeAllViews();
        answerLetters.clear();

        questionLetters = new ArrayList<>(Arrays.asList(vocab.getWORD().split("")));
        Collections.shuffle(questionLetters);

        for (String letter : questionLetters) {
            TextView letterView = createLetterTextView(letter);
            layoutLetterQuestion.addView(letterView);
            letterView.setOnClickListener(v -> moveToAnswerLayout(letterView, letter));
        }
    }

    private void loadVocabularyImage(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.word_thumnail)
                .error(R.drawable.word_thumnail)
                .into(imgWordTest);
    }

    private void setupAudioButton(String audioUrl) {
        btnSpeakerWordTest.setOnClickListener(view -> {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void moveToAnswerLayout(TextView letterView, String letter) {
        layoutLetterQuestion.removeView(letterView);
        layoutLetterAnswer.addView(letterView);
        answerLetters.add(letter);
        letterView.setOnClickListener(createReturnToQuestionListener(letterView, letter));
    }

    private View.OnClickListener createReturnToQuestionListener(TextView letterView, String letter) {
        return v -> {
            layoutLetterAnswer.removeView(letterView);
            layoutLetterQuestion.addView(letterView);
            answerLetters.remove(letter);
            letterView.setOnClickListener(v1 -> moveToAnswerLayout(letterView, letter));
        };
    }

    private TextView createLetterTextView(String letter) {
        TextView letterView = new TextView(getContext());
        letterView.setText(letter);
        letterView.setTextSize(24);
        letterView.setPadding(10, 10, 10, 10);
        letterView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.letter_background));
        return letterView;
    }

    private void checkAnswer() {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < layoutLetterAnswer.getChildCount(); i++) {
            TextView letterView = (TextView) layoutLetterAnswer.getChildAt(i);
            answer.append(letterView.getText().toString());
        }

        boolean isCorrect = answer.toString().equalsIgnoreCase(currentVocabulary.getWORD());
        showResultFragment(isCorrect);
    }

    private void showResultFragment(boolean isCorrect) {
        ResultWordTestFragment resultFragment = ResultWordTestFragment.newInstance(isCorrect);
        resultFragment.setOnNextListener(() -> {
            ViewWordTestActivity parentActivity = (ViewWordTestActivity) getActivity();
            if (parentActivity != null) {
                parentActivity.startWordTest(currentWordIndex + 1);
            }
        });
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        resultFragment.show(getFragmentManager(), "resultBottomSheet");
    }
}
