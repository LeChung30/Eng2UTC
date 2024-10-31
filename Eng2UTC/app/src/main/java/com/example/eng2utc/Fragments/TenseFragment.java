package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.R;

public class TenseFragment extends Fragment {

    private ImageView backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tense, container, false);
       backBtn = view.findViewById(R.id.btn_back_tense);
       backBtn.setOnClickListener(v -> {
           // Tạo instance của Fragments.GrammarFragment
           GrammarFragment grammarFragment = new GrammarFragment();

           // Thực hiện FragmentTransaction để thay thế Fragments.TenseFragment bằng Fragments.GrammarFragment
           FragmentTransaction transaction = getFragmentManager().beginTransaction();
           transaction.replace(R.id.frameLayout, grammarFragment); // Thay thế bằng Fragments.GrammarFragment
           transaction.addToBackStack(null); // Thêm vào back stack nếu cần thiết
           transaction.commit();
       });

       return view;
    }
}