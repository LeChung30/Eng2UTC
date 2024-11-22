package com.example.eng2utc.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Model.UserTest;
import com.example.eng2utc.R;
import com.example.eng2utc.TestExerciseActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<UserTest> userTestList;

    public HistoryAdapter(List<UserTest> userTestList) {
        this.userTestList = userTestList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_test, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        UserTest userTest = userTestList.get(position);
        holder.tvTestDate.setText("Ngày thi: " + userTest.getTEST_DATE());
        holder.tvScore.setText("Điểm: " + userTest.getSCORE() + "/40");
        holder.tvDescription.setText("Thời gian làm: " + userTest.getDURATION());
        holder.tvReview.setText("Xem lại");

        holder.tvReview.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TestExerciseActivity.class);
            intent.putExtra("test_id", userTest.getTEST_ID());
            intent.putExtra("user_test_id", userTest.getUSER_TEST_ID());
            intent.putExtra("review_mode", true); // Indicate review mode
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userTestList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestDate, tvScore, tvDescription, tvReview;

        HistoryViewHolder(View itemView) {
            super(itemView);
            tvTestDate = itemView.findViewById(R.id.tvTestDate);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvDescription = itemView.findViewById(R.id.tvDescripton);
            tvReview = itemView.findViewById(R.id.tvReview);
        }
    }
}