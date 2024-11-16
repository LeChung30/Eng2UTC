package com.example.eng2utc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eng2utc.R;
import com.example.eng2utc.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariables();
    }
    private void setVariables(){
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            if (password.length()<6){
                Toast.makeText(SignUpActivity.this, "Your password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()){
                        Log.i(TAG, "onComplete: Success"); // Đăng ký thành công, chuyển đến MainActivity
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }else {
                        Log.i(TAG, "onComplete: Fail" + task.getException());
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}