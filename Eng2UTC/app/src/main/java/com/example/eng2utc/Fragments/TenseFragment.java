package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.R;

public class TenseFragment extends Fragment {

    private ImageView backBtn;
    private CardView cardViewPresentSimple;
    private CardView cardViewPastSimple;
    private CardView cardViewFutureSimple;


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
        cardViewPresentSimple = view.findViewById(R.id.cardView_present_simple);
        cardViewPresentSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay thế TenseFragment bằng PresentSimpleFragment
                Fragment presentSimpleFragment = new PresentSimpleFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, presentSimpleFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cardViewPastSimple = view.findViewById(R.id.cardView_past_simple);
        cardViewPastSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Fragment pastSimpleFragment = new PastSimpleFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, pastSimpleFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            });

        cardViewFutureSimple = view.findViewById(R.id.cardView_future_simple);
        cardViewFutureSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Fragment futureSimpleFragment = new FutureSimpleFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, futureSimpleFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
       return view;
    }
}