package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eng2utc.Adapter.LessonAdapter;
import com.example.eng2utc.Adapter.LevelAdapter;
import com.example.eng2utc.Adapter.VocabularyAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.Lesson;
import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VocabularyForFragment extends Fragment {

    private VocabularyAdapter vocabularyAdapter;
    private List<Vocabulary> vocabularyList = new ArrayList<>();
    private ImageView backBtn;
    private RecyclerView vocabRcl;
    private FirebaseController firebaseController;

    public VocabularyForFragment(List<Vocabulary> vocabs) {
        this.vocabularyList = vocabs;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vocabulary_for, container, false);

        vocabRcl = view.findViewById(R.id.vocabForLesson);

        vocabRcl.setLayoutManager(new LinearLayoutManager(getContext()));
        vocabularyAdapter = new VocabularyAdapter(getContext(), vocabularyList );
        vocabRcl.setAdapter(vocabularyAdapter);
        vocabularyAdapter.notifyDataSetChanged();
        System.out.println(vocabularyList.size());

        backBtn = view.findViewById(R.id.btn_back_vocab_for_lesson);
        backBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước
        });
        return view;
    }
}