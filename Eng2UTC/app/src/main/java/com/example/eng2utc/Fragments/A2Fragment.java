package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Adapter.A2TestAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.Test;
import com.example.eng2utc.Model.TestType;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class A2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private A2TestAdapter adapter;
    private List<Test> testList;
    private FirebaseController firebaseController;
    private String testTypeID;

    public A2Fragment() {
        // Required empty public constructor
    }

    public A2Fragment(String testTypeID) {
        this.testTypeID = testTypeID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a2, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        testList = new ArrayList<>();
        firebaseController = new FirebaseController();

        // Setup RecyclerView with GridLayoutManager and adapter
        adapter = new A2TestAdapter(testList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        // Fetch data from Firebase
        firebaseController.getData("TEST", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                testList.clear(); // Clear the list before adding new items
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Test test = snapshot.getValue(Test.class); // Parse each item to Test model
                    // Check if the test is not null and has the same TEST_TYPE_ID
                    if (test != null && test.getTEST_TYPE_ID().equals(testTypeID)) {
                        testList.add(test);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error here, e.g., show a message
            }
        });

        return view;
    }
}
