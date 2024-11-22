package com.example.eng2utc.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.APIs.RequestManager;
import com.example.eng2utc.Dictionary.OnFetchDataListener;
import com.example.eng2utc.Dictionary.WordItem;
import com.example.eng2utc.DictionaryAdapter.MeaningAdapter;
import com.example.eng2utc.DictionaryAdapter.PhoneticAdapter;
import com.example.eng2utc.R;

public class SearchedWordActivity extends BaseActivity {

    private ImageView btn_back;
    private TextView txt_searchedWord;

    private RecyclerView recycler_meaing;
    private RecyclerView recycler_phonetic;
    private MeaningAdapter meaningAdapter;
    private PhoneticAdapter phoneticAdapter;

    private WordItem item;


    private String wordSearch;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_word);

        Intent intent = getIntent();
        if(intent!=null){
            wordSearch = intent.getStringExtra("query");  //
        }


        btn_back = findViewById(R.id.btn_back_searchedWord);
        btn_back.setOnClickListener(v -> finish());

        txt_searchedWord = findViewById(R.id.txt_searchedWord);

        recycler_phonetic = findViewById(R.id.phonetic_RecyclerView);
        recycler_meaing = findViewById(R.id.meaning_RecyclerView);

        //tao loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        //API
        RequestManager manager = new RequestManager(SearchedWordActivity.this);
        manager.getWordMeaning(listener,wordSearch);
        item = new WordItem();

    }

    private OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(WordItem wordItem, String message) {
            if (wordItem == null) {
                Toast.makeText(SearchedWordActivity.this, "no data found!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (wordItem != null) {
                showData(wordItem);
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(SearchedWordActivity.this,message,Toast.LENGTH_SHORT).show();
        }

    };

    private void showData(WordItem wordItem){
        txt_searchedWord.setText(wordItem.getWord());

        recycler_phonetic.setHasFixedSize(true);
        recycler_phonetic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        phoneticAdapter = new PhoneticAdapter(this, wordItem.getPhonetics());
        recycler_phonetic.setAdapter(phoneticAdapter);

        recycler_meaing.setHasFixedSize(true);
        recycler_meaing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        meaningAdapter = new MeaningAdapter(this, wordItem.getMeanings());
        recycler_meaing.setAdapter(meaningAdapter);

        progressDialog.dismiss();
    }

}