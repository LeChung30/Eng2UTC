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
import java.util.Collections;
import java.util.List;

public class ViewWordTestActivity extends AppCompatActivity {

    private List<Vocabulary> VocabList = new ArrayList<>();
    private int sizeVocabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_test);

        // Tải từ vựng
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VocabList = (List<Vocabulary>) bundle.getSerializable("vocabularyList");
            sizeVocabList = VocabList.size();
            Collections.shuffle(VocabList);
            startWordTest(0);
        }
    }

    // Phương thức để bắt đầu bài kiểm tra
    public void startWordTest(int index) {
        if (index < sizeVocabList) {
            // Lấy từ vựng hiện tại và tạo instance của WordTestFragment
            WordTestFragment fragment = WordTestFragment.newInstance(VocabList, index);

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
}
