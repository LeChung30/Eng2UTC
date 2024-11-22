package com.example.eng2utc.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eng2utc.Fragments.ExamForLevelFragment;
import com.example.eng2utc.Model.TestType;

import java.util.List;

public class ExamSelectionAdapter extends FragmentStateAdapter {

    private final List<TestType> testTypes;

    public ExamSelectionAdapter(@NonNull Fragment fragment, List<TestType> testTypes) {
        super(fragment);
        this.testTypes = testTypes;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Get TEST_TYPE_ID for the current position
        String testTypeId = testTypes.get(position).getTEST_TYPE_ID();
        return new ExamForLevelFragment(testTypeId);  // Pass TEST_TYPE_ID to the fragment
    }

    @Override
    public int getItemCount() {
        return testTypes.size();  // Number of test types determines the count
    }
}
