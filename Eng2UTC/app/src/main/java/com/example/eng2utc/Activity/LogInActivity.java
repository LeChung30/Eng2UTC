package com.example.eng2utc.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eng2utc.R;
import com.example.eng2utc.databinding.ActivityLoginBinding;

public class LogInActivity extends BaseActivity {

    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariables();
    }

    private void setVariables(){
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString().trim();
            String password = binding.passEdt.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInActivity.this, task -> {
                    if (task.isSuccessful()){
                        // save user data to shared preferences
                        String userId = mAuth.getCurrentUser().getUid();
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", userId);
                        editor.apply();

                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    } else {
                        // In ra lỗi cụ thể
                        Log.e(TAG, "Authentication failed: " + task.getException().getMessage());
                        Toast.makeText(LogInActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LogInActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}