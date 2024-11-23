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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        setVariables();
    }

    private void setVariables() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            String userName = binding.userNameEdt.getText().toString();
            String phone = binding.phoneEdt.getText().toString();

            // Kiểm tra Tên người dùng
            if (userName.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra số điện thoại
            if (phone.isEmpty() || !phone.matches("^\\d{10}$")) {  // Kiểm tra số điện thoại 10 chữ số
                Toast.makeText(SignUpActivity.this, "Vui lòng nhập số điện thoại hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra định dạng email (phải là @gmail.com)
            if (email.isEmpty() || !isValidEmail(email)) {
                Toast.makeText(SignUpActivity.this, "Vui lòng nhập email hợp lệ với định dạng @gmail.com", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra độ dài mật khẩu
            if (password.length() < 6) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu phải ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra email đã tồn tại trong Firebase Authentication
            checkEmailExists(email, password, userName, phone);
        });
    }

    // Phương thức kiểm tra xem email có bị trùng không
    private void checkEmailExists(String email, String password, String userName, String phone) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Nếu phương thức trả về một danh sách không rỗng, email đã được sử dụng
                List<String> signInMethods = task.getResult().getSignInMethods();
                if (signInMethods != null && !signInMethods.isEmpty()) {
                    // Nếu email đã được sử dụng, thông báo lỗi cho người dùng
                    Toast.makeText(SignUpActivity.this, "Email này đã được sử dụng!", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu email chưa được sử dụng, tiến hành đăng ký tài khoản
                    createUserAccount(email, password, userName, phone);
                }
            } else {
                // Xử lý khi có lỗi khi lấy phương thức đăng nhập
                Log.e(TAG, "Error checking email: ", task.getException());
                Toast.makeText(SignUpActivity.this, "Lỗi kiểm tra email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức tạo tài khoản mới
    private void createUserAccount(String email, String password, String userName, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "onComplete: Success");

                // Sau khi đăng ký thành công, lưu thông tin tên người dùng và số điện thoại vào Firebase Database
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // Tạo một đối tượng để lưu thông tin người dùng
                    UserProfile userProfile = new UserProfile(userName, phone, email, password);

                    // Thêm thông tin vào Firebase Realtime Database
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
                    database.child(user.getUid()).setValue(userProfile).addOnCompleteListener(databaseTask -> {
                        if (databaseTask.isSuccessful()) {
                            Log.i(TAG, "User profile added to database");
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.i(TAG, "Error saving user profile: " + databaseTask.getException());
                            Toast.makeText(SignUpActivity.this, "Lỗi lưu thông tin người dùng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                // Nếu đăng ký không thành công, kiểm tra lỗi và hiển thị thông báo
                if (task.getException() != null) {
                    String errorMessage = task.getException().getMessage();
                    // Kiểm tra nếu lỗi là do email đã tồn tại
                    if (errorMessage != null && errorMessage.contains("email address is already in use")) {
                        Toast.makeText(SignUpActivity.this, "Email này đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Đăng ký không thành công: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                Log.i(TAG, "onComplete: Fail" + task.getException());
            }
        });
    }

    //  Validate email
    private boolean isValidEmail(String email) {
        // Validate email với định dạng @gmail.com
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(emailPattern);
    }

    // Định nghĩa lớp UserProfile để lưu thông tin người dùng vào Firebase Database
    public static class UserProfile {
        public String userName;
        public String phone;
        public String email;
        public String password;

        public UserProfile() {
            // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
        }

        public UserProfile(String userName, String phone, String email, String password) {
            this.userName = userName;
            this.phone = phone;
            this.email = email;
            this.password = password;
        }
    }
}
