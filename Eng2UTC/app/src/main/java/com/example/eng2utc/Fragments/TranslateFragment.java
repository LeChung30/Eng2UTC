package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.eng2utc.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;

public class TranslateFragment extends Fragment {
    private Spinner translate_spinner_from;
    private Spinner translate_spinner_to;
    private TextInputEditText edt_translate;
    private ImageView translate_mic;
    private TextView tv_translate_result;

    String[] fromLanguage = {"English", "Vietnamese"};
    String[] toLanguage = {"English", "Vietnamese"};
    private static final int  REQUEST_PERMISSION_CODE = 1;
    int languageCode, fromLanguageCode, toLanguageCode = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        translate_spinner_from = view.findViewById(R.id.translate_spinner_from);
        translate_spinner_to = view.findViewById(R.id.translate_spinner_to);
        edt_translate = view.findViewById(R.id.edt_translate);
        translate_mic = view.findViewById(R.id.translate_mic);
        tv_translate_result = view.findViewById(R.id.tv_translate_result);

        translate_spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromLanguageCode = getLanguageCode(fromLanguage[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter fromAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translate_spinner_from.setAdapter(fromAdapter);

        translate_spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguageCode = getLanguageCode(toLanguage[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter toAdapter = new ArrayAdapter(this.getContext(), R.layout.spinner_item, toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translate_spinner_to.setAdapter(toAdapter);




        return view;
    }

    private int getLanguageCode(String language) {
        int languageCode = 0;
        switch (language) {
            case "Vietnamese":
                languageCode = FirebaseTranslateLanguage.VI;
                break;
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;
                break;

        }
        return languageCode;
    }
}
