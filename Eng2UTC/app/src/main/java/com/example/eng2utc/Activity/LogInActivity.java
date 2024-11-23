package com.example.eng2utc.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
    private TextView signUp_transferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariables();
        signUp_transferBtn = findViewById(R.id.signUp_transfer_btn);
        signUp_transferBtn.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void setVariables() {
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString().trim();
            String password = binding.passEdt.getText().toString().trim();

            // Kiểm tra các trường không được để trống
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LogInActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng email hợp lệ
            if (!isValidEmail(email)) {
                Toast.makeText(LogInActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài mật khẩu (nếu cần thiết, ví dụ ít nhất 6 ký tự)
            if (password.length() < 6) {
                Toast.makeText(LogInActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Nếu tất cả các kiểm tra đều hợp lệ, thực hiện đăng nhập
            signInUser(email, password);
        });
    }

    // Phương thức kiểm tra định dạng email hợp lệ
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Biểu thức chính quy để kiểm tra email
        return email.matches(emailPattern);
    }

    // Phương thức thực hiện đăng nhập với email và mật khẩu
    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInActivity.this, task -> {
            if (task.isSuccessful()) {
                // Đăng nhập thành công, lưu thông tin người dùng vào SharedPreferences
                String userId = mAuth.getCurrentUser().getUid();
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", userId);
                editor.apply();

                // Chuyển đến màn hình chính (MainActivity)
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                finish();  // Đóng màn hình đăng nhập sau khi chuyển
            } else {
                // Thay vì in ra mã lỗi, bạn chỉ cần thông báo lỗi chung
                Toast.makeText(LogInActivity.this, "Incorrect email or password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

