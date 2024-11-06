package com.example.eng2utc.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eng2utc.R;

import com.example.eng2utc.Fragments.ExamFragment;
import com.example.eng2utc.Fragments.HomeFragment;
import com.example.eng2utc.Fragments.TranslateFragment;
import com.example.eng2utc.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {

    HomeFragment homeFragment;
    //   Fragments.TranslateFragment translateFragment;
        ExamFragment examFragment;
    //    StatisticsFragment statisticsFragment;
    //    UserFragment userFragment;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        examFragment = new ExamFragment();
        replaceFragment(homeFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id =item.getItemId();
            if (id == R.id.bottom_nav_home) {
                replaceFragment(homeFragment);
            }
            else if (id == R.id.bottom_nav_translate) {
                replaceFragment(new TranslateFragment());
            }
            else if (id == R.id.bottom_nav_test) {
                replaceFragment(examFragment);
            }
            else if (id == R.id.bottom_nav_statistics) {
                replaceFragment(homeFragment);
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}