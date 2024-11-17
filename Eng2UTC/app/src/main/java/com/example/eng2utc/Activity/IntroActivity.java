package com.example.eng2utc.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eng2utc.R;
import com.example.eng2utc.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {

    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check login status
        if (mAuth.getCurrentUser() != null) {
            // Nếu đã đăng nhập, chuyển đến MainActivity
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish(); // Close IntroActivity to prevent users from returning to this page
        } else {
            setVariables();
        }

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    private void setVariables() {
        binding.loginBtn.setOnClickListener(v -> {
            if (mAuth.getCurrentUser()!= null){
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }else {
                startActivity(new Intent(IntroActivity.this, LogInActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignUpActivity.class)));

    }
}