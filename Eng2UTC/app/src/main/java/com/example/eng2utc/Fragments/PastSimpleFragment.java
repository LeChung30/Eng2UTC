package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eng2utc.R;

public class PastSimpleFragment extends Fragment {

    private ImageView backBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_simple, container, false);

        backBtn = view.findViewById(R.id.btn_back_past_simple);
        backBtn.setOnClickListener(v -> {
            // Tạo instance của Fragments.GrammarFragment
            TenseFragment tenseFragment = new TenseFragment();
            // Thực hiện FragmentTransaction để thay thế Fragments.TenseFragment bằng Fragments.GrammarFragment
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutContainer, tenseFragment); // Thay thế bằng Fragments.GrammarFragment
            transaction.addToBackStack(null); // Thêm vào back stack nếu cần thiết
            transaction.commit();
        });
        return view;
    }
}