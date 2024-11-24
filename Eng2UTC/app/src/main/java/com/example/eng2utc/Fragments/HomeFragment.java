package com.example.eng2utc.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.eng2utc.Activity.SearchedWordActivity;
import com.example.eng2utc.Activity.SettingActivity;
import com.example.eng2utc.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private ImageView btn_avatar;
    private CardView cardViewVocab;
    private CardView cardViewGrammar;
    private CardView cardViewExpressions;
    private SearchView searchView;
    private ProgressDialog progressDialog;


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
                // Thực hiện chuyển đến GrammarFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.home_fragment, new GrammarFragment()); // R.id.frameLayout2 là ID của layout chứa Fragment
                transaction.addToBackStack(null); // Để quay lại HomeFragment khi nhấn nút Back
                transaction.commit();
            }
        });

        cardViewVocab = view.findViewById(R.id.cardViewVocab);
        cardViewVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chuyển đến Fragments.VocabularyFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutContainer, new VocabularyFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Search new word
        progressDialog = new ProgressDialog(getContext());
        searchView = view.findViewById(R.id.search_dictionary);
        searchView.setQueryHint("Search word...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhấn nút tìm kiếm
                Intent intent = new Intent(getActivity(), SearchedWordActivity.class);
                intent.putExtra("query", query);
                progressDialog.setTitle("Loading...");
                progressDialog.show();
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi văn bản trong SearchView

                return false;
            }
        });

        return view;
    }
}