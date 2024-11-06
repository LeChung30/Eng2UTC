package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eng2utc.Adapter.LessonAdapter;
import com.example.eng2utc.Adapter.LevelAdapter;
import com.example.eng2utc.Adapter.VocabularyAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.CertLevel;
import com.example.eng2utc.Model.Lesson;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LessonFragment extends Fragment {
    private RecyclerView lessonRcl;
    private LessonAdapter lessonAdapter;
    private FirebaseController firebaseController;
    private String CertLevelID;
    private List<Lesson> listLesson;
    private ImageView btnBack;
    private ImageView imgLevel;

    public LessonFragment(String CertLevelID) {
        // Required empty public constructor
        this.CertLevelID = CertLevelID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        lessonRcl = view.findViewById(R.id.recyclerViewLesson);

        listLesson = new ArrayList<>();
        // Initialize RecyclerView
        lessonRcl.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonAdapter = new LessonAdapter(getContext(), listLesson );
        lessonRcl.setAdapter(lessonAdapter);

        // Firebase data retrieval
        firebaseController = new FirebaseController();
        firebaseController.getData("LESSON", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                listLesson.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Lesson lesson = snapshot.getValue(Lesson.class);
                    if (lesson != null) {
                        listLesson.add(lesson);
                    }
                }

                lessonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        btnBack = view.findViewById(R.id.btn_back_lesson);
        btnBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutContainer, new LevelBaseFragment());
            fragmentTransaction.commit();
        });

        return view;
    }
}