package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.eng2utc.R;

public class TranslateFragment extends Fragment {
    private LinearLayout parentLayout;
    private LinearLayout engTranslate;
    private LinearLayout vietnameseTranslate;
    private CardView englishCardView;
    private CardView vietnameseCardView;
    private boolean isEnglishFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        //Reference all views in Xml
        engTranslate = view.findViewById(R.id.eng_translate);
        vietnameseTranslate = view.findViewById(R.id.vietnamese_translate);
        englishCardView = view.findViewById(R.id.english_card_view);
        vietnameseCardView = view.findViewById(R.id.vietnamese_card_view);
        parentLayout = view.findViewById(R.id.parent_layout); // Make sure to set an id in your XML
        ImageView btn_switch_language = view.findViewById(R.id.btn_switch_language);

        // switch language Listener
        btn_switch_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapLanguages();
            }
            });
        return view;
    }

    private void swapLanguages() {
        ViewGroup parentLayout = (ViewGroup) engTranslate.getParent();
        ViewGroup cardParentLayout = (ViewGroup) englishCardView.getParent();

        // Check the initial positions
        int engIndex = parentLayout.indexOfChild(engTranslate);
        int vietIndex = parentLayout.indexOfChild(vietnameseTranslate);
        int englishCardIndex = cardParentLayout.indexOfChild(englishCardView);
        int vietnameseCardIndex = cardParentLayout.indexOfChild(vietnameseCardView);

        // Swap positions of engTranslate and vietnameseTranslate
        if (isEnglishFirst) {
            // Remove views
            parentLayout.removeView(engTranslate);
            parentLayout.removeView(vietnameseTranslate);

            // Add views in swapped order
            parentLayout.addView(vietnameseTranslate, engIndex);
            parentLayout.addView(engTranslate, vietIndex);

            // Swap positions of englishCardView and vietnameseCardView
            cardParentLayout.removeView(englishCardView);
            cardParentLayout.removeView(vietnameseCardView);

            // Add views in swapped order
            cardParentLayout.addView(vietnameseCardView, englishCardIndex);
            cardParentLayout.addView(englishCardView, vietnameseCardIndex);
        } else {
            // Remove views
            parentLayout.removeView(vietnameseTranslate);
            parentLayout.removeView(engTranslate);

            // Add views back to original order
            parentLayout.addView(engTranslate, vietIndex);
            parentLayout.addView(vietnameseTranslate, engIndex);

            // Swap positions of englishCardView and vietnameseCardView
            cardParentLayout.removeView(vietnameseCardView);
            cardParentLayout.removeView(englishCardView);

            // Add views back to original order
            cardParentLayout.addView(englishCardView, vietnameseCardIndex);
            cardParentLayout.addView(vietnameseCardView, englishCardIndex);
        }

        // Update flag
        isEnglishFirst = !isEnglishFirst;
    }

}