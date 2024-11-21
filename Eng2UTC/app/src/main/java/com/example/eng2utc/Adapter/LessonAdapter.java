package com.example.eng2utc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Model.Lesson;
import com.example.eng2utc.Model.Vocabulary;
import com.example.eng2utc.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    public interface OnItemClickListenerLesson {
        void onItemClick(List<Vocabulary> vocabs);
    }

    private Context context;
    private List<Lesson> listLesson;
    private OnItemClickListenerLesson listener; // Sửa kiểu thành OnItemClickListenerLesson

    public LessonAdapter(Context context, List<Lesson> listLesson) {
        this.context = context;
        this.listLesson = listLesson;
    }

    public void setOnItemClickListener(OnItemClickListenerLesson listener) {
        this.listener = listener; // Sửa kiểu khớp với interface
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    System.out.println("Lesson Adapter " + lesson.getVOCABULARY().size());
                    listener.onItemClick(lesson.getVOCABULARY());
                }
            }
        });
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
