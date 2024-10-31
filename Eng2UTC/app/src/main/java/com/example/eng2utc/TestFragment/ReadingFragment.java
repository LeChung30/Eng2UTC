package com.example.eng2utc.TestFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.eng2utc.R;

public class ReadingFragment extends Fragment {

    private static final String ARG_CONTENT = "content";
    private String content;

    public ReadingFragment() {
        // Required empty public constructor
    }

    public static ReadingFragment newInstance(String content) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content); // Gán nội dung vào Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_CONTENT); // Lấy nội dung từ Bundle
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);

        TextView readingTextView = view.findViewById(R.id.readingTextView); // Tìm TextView

        // Hiển thị nội dung
        if (content != null) {
            readingTextView.setText(content);
        } else {
            readingTextView.setText("No content available");
        }

        return view;
    }
}
