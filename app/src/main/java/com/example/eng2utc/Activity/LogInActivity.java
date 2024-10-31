package com.example.eng2utc.Activity;

import android.content.Intent;
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

public class LogInActivity extends AppCompatActivity {

    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariables();
    }

    private void setVariables(){
//        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = binding.userEdt.getText().toString();
//                String password = binding.passEdt.getText().toString();
//                if (!email.isEmpty() && !password.isEmpty()){
//                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LogInActivity.this, task -> {
//                        if (task.isSuccessful()){
//                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
//                        }
//                        else {
//                            Log.i(TAG, "onComplete: Fail" + task.getException());
//                            Toast.makeText(LogInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(LogInActivity.this, "Please fill all the fields",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}