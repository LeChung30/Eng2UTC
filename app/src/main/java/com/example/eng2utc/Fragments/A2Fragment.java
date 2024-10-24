package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Adapter.A2TestAdapter;
import com.example.eng2utc.R;

import java.util.ArrayList;
import java.util.List;


public class A2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private A2TestAdapter adapter;
    private List<String> testList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a2, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Dữ liệu giả lập - 5 bài thi A2
        testList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            testList.add("A2 Test " + i);
        }

        // Setup RecyclerView
        adapter = new A2TestAdapter(testList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}