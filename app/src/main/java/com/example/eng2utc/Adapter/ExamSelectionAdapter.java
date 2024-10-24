package com.example.eng2utc.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eng2utc.Fragments.A2Fragment;

public class ExamSelectionAdapter extends FragmentStateAdapter {

    public ExamSelectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo fragment tương ứng cho mỗi cấp độ
        switch (position) {
            case 0:
                return new A2Fragment();
            case 1:
                return new A2Fragment();
            case 2:
                return new A2Fragment();
            case 3:
                return new A2Fragment();
            case 4:
                return new A2Fragment();
            case 5:
                return new A2Fragment();
            default:
                return new A2Fragment();  // Mặc định là A1Fragment
        }
    }

    @Override
    public int getItemCount() {
        return 6;  // Có 6 cấp độ
    }
}
