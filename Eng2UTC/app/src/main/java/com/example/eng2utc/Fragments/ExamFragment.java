package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eng2utc.Adapter.ExamSelectionAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.TestType;
import com.example.eng2utc.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExamFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ExamSelectionAdapter pagerAdapter;
    private FirebaseController firebaseController = new FirebaseController(); // Khởi tạo FirebaseController
    private List<TestType> testTypes = new ArrayList<>(); // Lưu danh sách TestType

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Lấy danh sách TestType từ Firebase
        getTestType();

        return view;
    }

    private void getTestType() {
        firebaseController.getData("TEST_TYPE", new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                testTypes.clear(); // Xóa danh sách trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TestType testType = snapshot.getValue(TestType.class);
                    if (testType != null) {
                        testTypes.add(testType);
                    }
                }

                // Sắp xếp danh sách testTypes theo CERT_LEVEL_NAME
                Collections.sort(testTypes, new Comparator<TestType>() {
                    @Override
                    public int compare(TestType t1, TestType t2) {
                        return t1.getCERT_LEVEL_NAME().compareTo(t2.getCERT_LEVEL_NAME());
                    }
                });

                // Thiết lập TabLayout và ViewPager sau khi có dữ liệu
                setupTabLayoutAndViewPager();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Xử lý lỗi nếu không thể lấy dữ liệu từ Firebase
            }
        });
    }

    private void setupTabLayoutAndViewPager() {
        // Thêm các tab cho TabLayout theo CERT_LEVEL_NAME
        for (TestType testType : testTypes) {
            tabLayout.addTab(tabLayout.newTab().setText(testType.getCERT_LEVEL_NAME()));
        }

        // Thiết lập ViewPager Adapter
        pagerAdapter = new ExamSelectionAdapter(this, testTypes);
        viewPager.setAdapter(pagerAdapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position < testTypes.size()) {
                tab.setText(testTypes.get(position).getCERT_LEVEL_NAME());
            }
        }).attach();

        // Xử lý sự kiện khi nhấn vào một tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                // Code thay đổi nội dung tương ứng với mỗi tab
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
