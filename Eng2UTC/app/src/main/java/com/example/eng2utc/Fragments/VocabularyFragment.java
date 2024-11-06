package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.eng2utc.R;

public class VocabularyFragment extends Fragment {
    private ImageView backBtn;
    private Button btnLevel;
    private Button btnTest;
    private Button btnTheme;
    private Button btnIdiom;

    public VocabularyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);

        backBtn = view.findViewById(R.id.btn_back_vocab);
        backBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutContainer, new HomeFragment()); // Thay  frameLayout2 bằng ID của layout chứa fragment
            fragmentTransaction.commit();
        });

        btnLevel = view.findViewById(R.id.btn_levelbase);
        btnLevel.setOnClickListener(v -> {
            LevelBaseFragment levelBaseFragment = new LevelBaseFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutContainer, levelBaseFragment);
            transaction.addToBackStack(null); // Để có thể quay lại fragment trước đó
            transaction.commit();
        });
        return view;


    }
}