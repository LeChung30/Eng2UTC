package com.example.eng2utc.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eng2utc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class TranslateFragment extends Fragment {
    private Spinner translate_spinner_from;
    private Spinner translate_spinner_to;
    private TextInputEditText edt_translate_from;
    private ImageView translate_mic;
    private Button btn_translate;
    private TextView tv_translate_result;

    String[] fromLanguage = {"English", "Vietnamese"};
    String[] toLanguage = {"Vietnamese", "English"};
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
        edt_translate_from = view.findViewById(R.id.edt_translate);
        translate_mic = view.findViewById(R.id.translate_mic);
        tv_translate_result = view.findViewById(R.id.tv_translate_result);
        btn_translate = view.findViewById(R.id.translateBtn);
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

        translate_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something to translate");
                try {
                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);;
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TranslateFragment.this.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_translate_result.setVisibility(View.VISIBLE);
                tv_translate_result.setText("");
                if (Objects.requireNonNull(edt_translate_from.getText()).toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter text to translate", Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode == 0) {
                    Toast.makeText(requireContext(), "Please select source language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode == 0) {
                    Toast.makeText(requireContext(), "Please select target language", Toast.LENGTH_SHORT).show();
                } else {
                    translateText(fromLanguageCode, toLanguageCode, edt_translate_from.getText().toString());
                }
            }
        });
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void translateText(int fromLanguageCode, int toLanguageCode, String source) {
        tv_translate_result.setText("Downloading model, please wait...");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode)
                .build();
        FirebaseTranslator translator  = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tv_translate_result.setText("Translation...");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        tv_translate_result.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(),"Failure to translate "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),"Failed to download .Check your internet connection "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE){
            assert data != null;
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            assert result != null;
            edt_translate_from.setText(result.get(0));
        }
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