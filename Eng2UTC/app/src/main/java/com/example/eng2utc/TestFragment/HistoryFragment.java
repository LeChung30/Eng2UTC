package com.example.eng2utc.TestFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Adapter.HistoryAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.UserTest;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends DialogFragment {

    private static final String ARG_TEST_ID = "test_id";
    private String testId;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<UserTest> userTestList;
    private FirebaseController firebaseController;
    private String userId;

    public static HistoryFragment newInstance(String testId) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEST_ID, testId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        if (getArguments() != null) {
            testId = getArguments().getString(ARG_TEST_ID);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        userTestList = new ArrayList<>();
        firebaseController = new FirebaseController();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter(userTestList);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "Unknown User");
        System.out.println("User ID: " + userId);

        fetchUserTests(userId);

        return view;
    }

    private void fetchUserTests(String userId) {
        firebaseController.getData("USER_TEST", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                userTestList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTest userTest = snapshot.getValue(UserTest.class);
                    if (userTest != null && userTest.getTEST_ID().equals(testId)
                            && userTest.getUSER_ID().equals(userId)) {
                        userTestList.add(userTest);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error here, e.g., show a message
            }
        });
    }
}