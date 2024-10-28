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

    private List<Vocabulary> vocabularyList;   // Danh sách từ vựng
    private int currentWordIndex;              // Chỉ số của từ vựng hiện tại
    private Vocabulary currentVocabulary;      // Từ vựng hiện tại

    private LinearLayout layoutLetterAnswer, layoutLetterQuestion;
    private ImageView img_word_test_fragment;
    private Button checkButton;
    private ImageButton btn_speaker_word_test;
    private List<String> answerLetters = new ArrayList<>();
    private List<String> questionLetters = new ArrayList<>();

    public WordTestFragment() {
        // Required empty public constructor
    }

    // Tạo một instance mới và truyền danh sách từ vựng và chỉ số hiện tại vào
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
            currentVocabulary = vocabularyList.get(currentWordIndex); // Lấy từ hiện tại
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_test, container, false);

        layoutLetterAnswer = view.findViewById(R.id.layout_letter_answer);
        layoutLetterQuestion = view.findViewById(R.id.layout_letter_question);
        checkButton = view.findViewById(R.id.checkButton);
        img_word_test_fragment = view.findViewById(R.id.img_word_test_fragment);
        btn_speaker_word_test = view.findViewById(R.id.btn_speaker_word_test);

        // Set từ vựng cho bài test
        setVocabularyData(currentVocabulary);

        // Xử lý khi nhấn nút kiểm tra
        checkButton.setOnClickListener(v -> checkAnswer());

        return view;
    }

    private void setVocabularyData(Vocabulary vocab) {
        // Load image using Picasso
        Picasso.get()
                .load(vocab.getIMAGE_LINK()) // URL hình ảnh
                .placeholder(R.drawable.word_thumnail) // Hình ảnh tạm thời
                .error(R.drawable.word_thumnail) // Hình ảnh hiển thị khi có lỗi
                .into(img_word_test_fragment);

        // Set onClickListener for audio button
        btn_speaker_word_test.setOnClickListener(view -> {
            // Play audio using the AUDIO_LINK
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(vocab.getAUDIO_LINK());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Xóa các ký tự hiện tại trong layout
        layoutLetterAnswer.removeAllViews();
        layoutLetterQuestion.removeAllViews();
        answerLetters.clear();

        // Chuyển từ thành danh sách các ký tự
        String word = vocab.getWORD();
        questionLetters = new ArrayList<>(Arrays.asList(word.split("")));

        // Xáo trộn các ký tự trong câu hỏi
        Collections.shuffle(questionLetters);

        // Hiển thị từng ký tự trong layout_letter_question
        for (String letter : questionLetters) {
            TextView letterView = createLetterTextView(letter);
            layoutLetterQuestion.addView(letterView);

            // Khi nhấn vào chữ trong câu hỏi, chuyển nó sang layout_answer
            letterView.setOnClickListener(v -> {
                layoutLetterQuestion.removeView(letterView);
                layoutLetterAnswer.addView(letterView);
                answerLetters.add(letter);

                // Thêm sự kiện khi nhấn vào chữ trong answer để chuyển ngược lại
                letterView.setOnClickListener(answerClickListener(letterView, letter));
            });
        }
    }

    private View.OnClickListener answerClickListener(TextView letterView, String letter) {
        return v -> {
            // Xóa chữ khỏi layout_letter_answer và chuyển nó lại layout_letter_question
            layoutLetterAnswer.removeView(letterView);
            layoutLetterQuestion.addView(letterView);
            answerLetters.remove(letter);

            // Thêm lại sự kiện chuyển chữ từ question sang answer
            letterView.setOnClickListener(v1 -> {
                layoutLetterQuestion.removeView(letterView);
                layoutLetterAnswer.addView(letterView);
                answerLetters.add(letter);

                // Lại gán sự kiện khi nhấn để chuyển ngược lại
                letterView.setOnClickListener(answerClickListener(letterView, letter));
            });
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
        // Ghép lại các chữ trong câu trả lời
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < layoutLetterAnswer.getChildCount(); i++) {
            TextView letterView = (TextView) layoutLetterAnswer.getChildAt(i);
            answer.append(letterView.getText().toString());
        }

        // Kiểm tra nếu câu trả lời khớp với từ gốc
        boolean isCorrect = answer.toString().equalsIgnoreCase(currentVocabulary.getWORD());

        // Hiển thị ResultWordTestFragment với kết quả đúng hoặc sai
        ResultWordTestFragment resultFragment = ResultWordTestFragment.newInstance(isCorrect);

        // Lắng nghe sự kiện "Next" từ ResultWordTestFragment
        resultFragment.setOnNextListener(() -> {
            int currentIndex = getArguments().getInt(ARG_INDEX);
            ViewWordTestActivity parentActivity = (ViewWordTestActivity) getActivity();
            if (parentActivity != null) {
                parentActivity.startWordTest(currentIndex + 1); // Bắt đầu từ từ vựng tiếp theo
            }
        });

        // Hiển thị BottomSheet ResultWordTestFragment
        resultFragment.show(getFragmentManager(), "resultBottomSheet");
    }

}
