package com.example.eng2utc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Activity.TestActivity;
import com.example.eng2utc.Model.CertLevel;
import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder>{
    private Context context;
    private List<CertLevel> certLevelList;

    public LevelAdapter(Context context, List<CertLevel> certLevelList) {
        this.context = context;
        this.certLevelList = certLevelList;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_levelbase, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.LevelViewHolder holder, int position) {
        CertLevel level = certLevelList.get(position);
        holder.levelName.setText("Danh từ cấp độ " + level.getLEVEL_NAME());
        holder.levelDesc.setText(level.getDESCRIPTION());

        // Load image using Picasso
        Picasso.get()
                .load(level.getIMAGE_LINK()) // URL hình ảnh
                .placeholder(R.drawable.word_thumnail) // Hình ảnh tạm thời
                .error(R.drawable.word_thumnail) // Hình ảnh hiển thị khi có lỗi
                .into(holder.levelImageView);
    }

    @Override
    public int getItemCount() {
        return certLevelList.size();
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        ImageView levelImageView;
        TextView levelName, levelDesc;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            levelImageView = itemView.findViewById(R.id.word_level_img);
            levelName = itemView.findViewById(R.id.word_level_title);
            levelDesc = itemView.findViewById(R.id.word_level_desc);
        }
    }
}
