package com.example.eng2utc.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabViewHolder> {
    private Context context;
    private List<Vocabulary> vocabularyList;

    public VocabularyAdapter(Context context, List<Vocabulary> vocabList) {
        this.context = context;
        this.vocabularyList = vocabList;
    }

    @NonNull
    @Override
    public VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vocabulary, parent, false);
        return new VocabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabViewHolder holder, int position) {
        Vocabulary vocab = vocabularyList.get(position);
        holder.wordTextView.setText(vocab.getWORD());
        holder.partOfSpeechTextView.setText(vocab.getPART_OF_SPEECH());
        holder.meaningTextView.setText(vocab.getMEANING());
        holder.pronunciationTextView.setText(vocab.getPRONUNCIATION());

        // Load image using Picasso
        Picasso.get()
                .load(vocab.getIMAGE_LINK()) // URL hình ảnh
                .placeholder(R.drawable.word_thumnail) // Hình ảnh tạm thời
                .error(R.drawable.word_thumnail) // Hình ảnh hiển thị khi có lỗi
                .into(holder.vocabImageView);

        // Set onClickListener for audio button
        holder.imageButton.setOnClickListener(view -> {
            // Play audio using the AUDIO_LINK
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(vocab.getAUDIO_LINK());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabularyList.size();
    }

    static class VocabViewHolder extends RecyclerView.ViewHolder {
        ImageView vocabImageView;
        TextView wordTextView, pronunciationTextView, partOfSpeechTextView, meaningTextView;
        ImageButton imageButton;

        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            vocabImageView = itemView.findViewById(R.id.vocabImageView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            pronunciationTextView = itemView.findViewById(R.id.pronunciationTextView);
            partOfSpeechTextView = itemView.findViewById(R.id.partOfSpeechTextView);
            meaningTextView = itemView.findViewById(R.id.meaningTextView);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }
}