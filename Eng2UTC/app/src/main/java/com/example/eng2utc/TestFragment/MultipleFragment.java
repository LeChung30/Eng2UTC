package com.example.eng2utc.TestFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.eng2utc.Model.PartDetail;
import com.example.eng2utc.R;
import com.squareup.picasso.Picasso;

public class MultipleFragment extends Fragment {

    private static final String ARG_PART_DETAIL = "partDetail";

    private PartDetail partDetail;

    public MultipleFragment() {
        // Required empty public constructor
    }

    public static MultipleFragment newInstance(PartDetail partDetail) {
        MultipleFragment fragment = new MultipleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PART_DETAIL, partDetail); // Gán đối tượng PartDetail vào Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            partDetail = (PartDetail) getArguments().getSerializable(ARG_PART_DETAIL); // Lấy PartDetail từ Bundle
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        ImageView imageView = view.findViewById(R.id.imageView);

        // Hiển thị thông tin từ PartDetail
//        if (partDetail != null) {
//            titleTextView.setText(partDetail.getTitle()); // Hiển thị tiêu đề
//            descriptionTextView.setText(partDetail.getDescription()); // Hiển thị mô tả
//
//            // Hiển thị hình ảnh
//            if (partDetail.getImageUrl() != null && !partDetail.getImageUrl().isEmpty()) {
//                Picasso.get().load(partDetail.getImageUrl()).into(imageView); // Sử dụng Picasso để tải ảnh
//            } else {
//                // Nếu không có URL hình ảnh, có thể ẩn ImageView hoặc đặt một hình ảnh mặc định
//                imageView.setVisibility(View.GONE); // Ẩn ImageView nếu không có ảnh
//            }
//        }

        return view;
    }
}
