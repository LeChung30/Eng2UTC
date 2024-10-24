package com.example.eng2utc.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.R;

import java.util.List;

import com.example.eng2utc.Activity.TestActivity;

public class A2TestAdapter extends RecyclerView.Adapter<A2TestAdapter.TestViewHolder> {

    private List<String> testList;

    public A2TestAdapter(List<String> testList) {
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the CardView item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_a2, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        // Bind the data to the TextView in each CardView
        String testName = testList.get(position);
        holder.tvTestName.setText(testName);
        holder.itemView.setOnClickListener(v -> {
             Intent intent = new Intent(v.getContext(), TestActivity.class);
            intent.putExtra("testName", testName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();  // Return the size of the list
    }

    // ViewHolder class for RecyclerView
    public static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTestName = itemView.findViewById(R.id.tvTestName);
        }
    }
}
