package com.example.eng2utc.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.Activity.SettingActivity;
import com.example.eng2utc.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private ImageView btn_avatar;
    private CardView cardViewVocab;
    private CardView cardViewGrammar;
    private CardView cardViewExpressions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView avatar = view.findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Settings activity
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        cardViewGrammar = view.findViewById(R.id.cardViewGrammar);
        cardViewGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chuyển đến Fragments.GrammarFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.home_fragment, new GrammarFragment()); // R.id.frameLayout2 là ID của layout chứa Fragment
                transaction.addToBackStack(null); // Để quay lại Fragments.HomeFragment khi nhấn nút Back
                transaction.commit();
            }
        });

        return view;
    }
}