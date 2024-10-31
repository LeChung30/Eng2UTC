package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.eng2utc.Adapter.LevelAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.CertLevel;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;


public class LevelBaseFragment extends Fragment {

    private RecyclerView levelRcl;
    private LevelAdapter levelAdapter;
    private FirebaseController firebaseController;
    private ArrayList<CertLevel> certLevelArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level_base, container, false);
        levelRcl = view.findViewById(R.id.recyclerViewWordLevel);

        // Initialize RecyclerView
        levelRcl.setLayoutManager(new LinearLayoutManager(getContext()));
        levelAdapter = new LevelAdapter(getContext(), certLevelArrayList );
        levelRcl.setAdapter(levelAdapter);

        // Firebase data retrieval
        firebaseController = new FirebaseController();
        firebaseController.getLevelData(new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // Clear the list before adding new data
                certLevelArrayList.clear();

                // Iterate through the snapshot and populate the certLevelArrayList
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CertLevel certLevel = snapshot.getValue(CertLevel.class);
                    if (certLevel != null) {
                        certLevelArrayList.add(certLevel);
                    }
                }

                // Notify the adapter of data changes
                levelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error (e.g., show a Toast or log the error)
                Log.e("LevelBaseFragment", "Error retrieving data: " + errorMessage);
            }
        });

        return view;
    }
}