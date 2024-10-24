package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.R;

public class GrammarFragment extends Fragment {

    private ImageView backBtn;
    private CardView cardView_tense;
    private CardView cardView_adj;
    private CardView cardView_phrase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grammar, container, false);

        backBtn = view.findViewById(R.id.btn_back_grammar);
        backBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()); // Thay  frameLayout2 bằng ID của layout chứa fragment
            fragmentTransaction.commit();
        });

        cardView_tense = view.findViewById(R.id.cardView_tense);
        cardView_tense.setOnClickListener(v -> {
            // Chuyển sang Fragments.TenseFragment khi nhấn vào CardView
            TenseFragment tenseFragment = new TenseFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, tenseFragment);
            transaction.addToBackStack(null); // Để có thể quay lại fragment trước đó
            transaction.commit();
        });

        return view;

    }
}