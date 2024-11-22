package com.example.eng2utc.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Model.Test;
import com.example.eng2utc.Model.UserTest;
import com.example.eng2utc.R;
import com.example.eng2utc.TestExerciseActivity;
import com.example.eng2utc.TestFragment.HistoryFragment;

import java.util.List;

public class LevelTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Test> testList;
    private List<UserTest> userTests;

    public LevelTestAdapter(List<Test> testList, List<UserTest> userTests) {
        this.testList = testList;
        this.userTests = userTests;
    }

    @Override
    public int getItemViewType(int position) {
        Test test = testList.get(position);
        for (UserTest userTest : userTests) {
            if (userTest.getTEST_ID().equals(test.getTEST_ID())) {
                return R.layout.cardview_changed;
            }
        }
        return R.layout.cardview_unchanged;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.cardview_changed) {
            return new ChangedViewHolder(view);
        } else {
            return new UnchangedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Test test = testList.get(position);
        if (holder instanceof ChangedViewHolder) {
            ((ChangedViewHolder) holder).bind(test, userTests);
        } else {
            ((UnchangedViewHolder) holder).bind(test);
        }
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class ChangedViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestName, score, status, retry;
        ImageButton history;

        ChangedViewHolder(View itemView) {
            super(itemView);
            tvTestName = itemView.findViewById(R.id.tvTestName);
            score = itemView.findViewById(R.id.score);
            status = itemView.findViewById(R.id.status);
            retry = itemView.findViewById(R.id.retry);
            history = itemView.findViewById(R.id.history);
        }

        void bind(Test test, List<UserTest> userTests) {
            tvTestName.setText(test.getNAME_OF_TEST());

            // take last userTest
            UserTest userTest = userTests.get(userTests.size() - 1);
            score.setText(userTest.getSCORE() + "/40");
            status.setText("Completed");
            retry.setText("Retry");

            // Set click listener for history button
            history.setOnClickListener(v -> {
                // Open history fragment as a dialog
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                HistoryFragment historyFragment = HistoryFragment.newInstance(userTest.getTEST_ID());
                historyFragment.show(fragmentManager, "history_fragment");
            });

            // Set click listener for retry button
            retry.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), TestExerciseActivity.class);
                intent.putExtra("test_id", test.getTEST_ID() + "");
                v.getContext().startActivity(intent);
            });
        }
    }

    class UnchangedViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestName, tvOpenTest;

        UnchangedViewHolder(View itemView) {
            super(itemView);
            tvTestName = itemView.findViewById(R.id.tvTestName);
            tvOpenTest = itemView.findViewById(R.id.tv_open_test);
        }

        void bind(Test test) {
            tvTestName.setText(test.getNAME_OF_TEST());
            tvOpenTest.setText("Take Test Now");
            tvOpenTest.setOnClickListener(v -> {
                // open test
                Intent intent = new Intent(v.getContext(), TestExerciseActivity.class);
                intent.putExtra("test_id", test.getTEST_ID() + "");
                v.getContext().startActivity(intent);
            });
        }
    }

    public void updateUserTests(List<UserTest> userTests) {
        this.userTests = userTests;
    }
}