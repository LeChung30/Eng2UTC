package com.example.eng2utc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eng2utc.Adapter.ExamSelectionAdapter;
import com.example.eng2utc.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExamFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ExamSelectionAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        // Thêm các tab cho TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("A1"));
        tabLayout.addTab(tabLayout.newTab().setText("A2"));
        tabLayout.addTab(tabLayout.newTab().setText("B1"));
        tabLayout.addTab(tabLayout.newTab().setText("B2"));
        tabLayout.addTab(tabLayout.newTab().setText("C1"));
        tabLayout.addTab(tabLayout.newTab().setText("C2"));
        // Thiết lập ViewPager Adapter
        pagerAdapter = new ExamSelectionAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("A1");
                    break;
                case 1:
                    tab.setText("A2");
                    break;
                case 2:
                    tab.setText("B1");
                    break;
                case 3:
                    tab.setText("B2");
                    break;
                case 4:
                    tab.setText("C1");
                    break;
                case 5:
                    tab.setText("C2");
                    break;
            }
        }).attach();

        // Xử lý sự kiện khi nhấn vào một tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn, ví dụ thay đổi nội dung hiển thị
                int position = tab.getPosition();
                // Code thay đổi nội dung tương ứng với mỗi tab
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Xử lý khi tab bị bỏ chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn lại
            }
        });

        return view;
    }
}