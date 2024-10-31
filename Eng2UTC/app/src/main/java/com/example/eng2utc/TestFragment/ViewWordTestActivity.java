package com.example.eng2utc.TestFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Adapter.VocabularyAdapter;
import com.example.eng2utc.Firebase.FirebaseController;
import com.example.eng2utc.Firebase.FirebaseDataCallback;
import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewWordTestActivity extends AppCompatActivity {
    private FirebaseController firebaseController;
    private RecyclerView recyclerView;
    private VocabularyAdapter vocabAdapter;
    private List<Vocabulary> a2VocabList = new ArrayList<>();
    private Button btnStartWordTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_test);

        // Ánh xạ RecyclerView và Button từ layout
        recyclerView = findViewById(R.id.rc_view_vocab);
        btnStartWordTest = findViewById(R.id.btn_start_word_test);

        // Thiết lập layout manager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo FirebaseController
        firebaseController = new FirebaseController();

        // Tải từ vựng A2 từ Firebase
        loadA2Vocabulary();

        // Sự kiện khi nhấn nút bắt đầu kiểm tra từ vựng
        btnStartWordTest.setOnClickListener(v -> {
            if (!a2VocabList.isEmpty()) {
                // Ẩn RecyclerView và Button sau khi bắt đầu bài kiểm tra
                recyclerView.setVisibility(View.GONE);
                btnStartWordTest.setVisibility(View.GONE);

                // Bắt đầu bài kiểm tra với từ vựng đầu tiên
                startWordTest(0);
            } else {
                Toast.makeText(ViewWordTestActivity.this, "Danh sách từ vựng trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức để bắt đầu bài kiểm tra
    public void startWordTest(int index) {
        if (index < a2VocabList.size()) {
            // Lấy từ vựng hiện tại và tạo instance của WordTestFragment
            Vocabulary vocab = a2VocabList.get(index);
            WordTestFragment fragment = WordTestFragment.newInstance(a2VocabList, index);

            // Chuyển đến WordTestFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_word_test, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // Thông báo khi hoàn thành bài kiểm tra
            Toast.makeText(this, "Bài kiểm tra đã hoàn thành!", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để tải từ vựng A2 từ Firebase
    private void loadA2Vocabulary() {
        firebaseController.getA2VocabularyData(new FirebaseDataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                a2VocabList.clear(); // Xóa danh sách hiện tại trước khi tải dữ liệu mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Vocabulary vocab = snapshot.getValue(Vocabulary.class);
                    if (vocab != null) {
                        a2VocabList.add(vocab); // Thêm từ vựng vào danh sách
                    }
                }

                // Gán adapter cho RecyclerView
                vocabAdapter = new VocabularyAdapter(ViewWordTestActivity.this, a2VocabList);
                recyclerView.setAdapter(vocabAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(ViewWordTestActivity.this, "Không thể tải dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
