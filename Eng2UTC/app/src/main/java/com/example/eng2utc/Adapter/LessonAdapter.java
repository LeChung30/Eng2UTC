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

import com.example.eng2utc.Model.Lesson;
import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private Context context;
    private List<Lesson> listLesson;

    public LessonAdapter(Context context, List<Lesson> listLesson) {
        this.context = context;
        this.listLesson = listLesson;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = listLesson.get(position);
        holder.lessonTitle.setText(lesson.getNAME_OF_LESSON());
        holder.lessonDesc.setText(lesson.getTOPIC_NAME());
    }

    @Override
    public int getItemCount() {
        return listLesson.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle, lessonDesc;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            lessonDesc = itemView.findViewById(R.id.lesson_desc);
        }
    }
}
