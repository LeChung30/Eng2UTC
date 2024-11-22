package com.example.eng2utc.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Adapter.LevelTestAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.Test;
import com.example.eng2utc.Model.UserTest;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExamForLevelFragment extends Fragment {

    private RecyclerView rclExamForLevel;
    private LevelTestAdapter adapter;
    private List<Test> testList = new ArrayList<>();
    private FirebaseController firebaseController;
    private String testTypeID;

    public ExamForLevelFragment() {
        // Required empty public constructor
    }

    public ExamForLevelFragment(String testTypeID) {
        this.testTypeID = testTypeID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam_for_level, container, false);

        rclExamForLevel = view.findViewById(R.id.rcv_exam_for_level);
        adapter = new LevelTestAdapter(testList, new ArrayList<>());
        rclExamForLevel.setAdapter(adapter);
        firebaseController = new FirebaseController();

        // Setup RecyclerView with GridLayoutManager and adapter
        rclExamForLevel.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Fetch data from Firebase
        fetchTests();

        return view;
    }

    private void fetchTests() {
        firebaseController.getData("TEST", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                testList.clear(); // Clear the list before adding new items
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Test test = snapshot.getValue(Test.class); // Parse each item to Test model
                    // Check if the test is not null and has the same TEST_TYPE_ID
                    if (test != null && test.getTEST_TYPE_ID() != null && test.getTEST_TYPE_ID().equals(testTypeID)) {
                        testList.add(test);
                    }
                }

                fetchUserTests();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error here, e.g., show a message
                Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserTests() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "Unknown User");

        firebaseController.getData("USER_TEST", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<UserTest> userTests = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTest userTest = snapshot.getValue(UserTest.class);
                    if (userTest != null && userTest.getUSER_ID().equals(userId)) {
                        userTests.add(userTest);
                    }
                }
                adapter.updateUserTests(userTests);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error here, e.g., show a message
                Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshData() {
        fetchUserTests();
    }
}