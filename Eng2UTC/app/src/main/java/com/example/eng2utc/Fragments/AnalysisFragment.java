package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.eng2utc.Model.MemoryLevel;
import com.example.eng2utc.R;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


import com.example.eng2utc.Retrofit.RetrofitController;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BarChart memoryBarChart;

    private TextView streaksTextView;
    private TextView studiedWordsTextView;
    RequestBody body = RequestBody.create(
            MediaType.parse("application/json"),
            "{\"user_id\":\"00a287f7-cc71-4c8c-b957-07b80fd92b28\",\"attending_date\":\"2024-02-08 00:00:00\"}"
    );
    //get id of the textview in the xml file at

    public AnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        displayStreaks();
        show_total_studied_words();
        display_bar_chart_memory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        // Initialize BarChart
        streaksTextView = view.findViewById(R.id.analysis_textview_streak);
        studiedWordsTextView = view.findViewById(R.id.analysis_textview_studied_words);
        memoryBarChart = view.findViewById(R.id.memoryBarChart);

        return view;
        //test key
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void displayStreaks() {
        //display streaks
        RetrofitController.getApiService().getStreak("00a287f7-cc71-4c8c-b957-07b80fd92b28", "2024-09-08").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer streak = response.body();
                    streaksTextView.setText("Streaks: " + streak.toString());
                } else {
                    System.out.printf("Failed to get streaks");
                    //print error message
                    System.out.printf(response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.printf("Failed to get streaks");
                //print error message
                System.out.printf(t.getMessage());
            }
        });
    }

    private void show_total_studied_words() {
        //display total studied words
        RetrofitController.getApiService().getTotalWords("00a287f7-cc71-4c8c-b957-07b80fd92b28").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer totalWords = response.body();
                    studiedWordsTextView.setText("Total Studied Words: " + totalWords.toString());
                } else {
                    System.out.printf("Failed to get total studied words");
                    //print error message
                    System.out.printf(response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.printf("Failed to get total studied words");
                //print error message
                System.out.printf(t.getMessage());
            }
        });

    }

    private void display_bar_chart_memory() {
        // Initialize BarChart
        final ArrayList<MemoryLevel>[] memoryLevels = new ArrayList[]{null};
        RetrofitController.getApiService().getMemoryLevel("00a287f7-cc71-4c8c-b957-07b80fd92b28").enqueue(new Callback<List<MemoryLevel>>() {
            @Override
            public void onResponse(Call<List<MemoryLevel>> call, Response<List<MemoryLevel>> response) {
                if (response.isSuccessful()) {
                    memoryLevels[0] = new ArrayList<>(response.body());
                    setupBarChart(memoryLevels[0]);
                } else {
                    System.out.printf("Failed to get memory levels");
                    //print error message
                    System.out.printf(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MemoryLevel>> call, Throwable t) {
                System.out.printf("Failed to get memory levels");
                //print error message
                System.out.printf(t.getMessage());
            }
        });
    }


    private void setupBarChart(ArrayList<MemoryLevel> memoryLevels) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        labels.add("");

        for (MemoryLevel memoryLevel : memoryLevels) {
            entries.add(new BarEntry(memoryLevel.getLevel(), memoryLevel.getCount()));
            labels.add(memoryLevel.getLabel()); // Lấy nhãn từ phương thức getLabel()
        }

        BarDataSet dataSet = new BarDataSet(entries, "Memory Level");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f); // Đặt kích thước văn bản của giá trị
        BarData data = new BarData(dataSet);
        memoryBarChart.setData(data);

        // Thiết lập nhãn cho trục X
        XAxis xAxis = memoryBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f); // Đảm bảo mỗi nhãn là một giá trị duy nhất
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f); // Đảm bảo trục X bắt đầu từ vị trí đầu tiên
        memoryBarChart.setExtraOffsets(10, 10, 10, 15); // Thêm khoảng trống xung quanh biểu đồ
        xAxis.setTextSize(12f); // Đặt kích thước văn bản của trục X

        // Thiết lập nhãn cho trục Y
        YAxis leftAxis = memoryBarChart.getAxisLeft();
        leftAxis.setTextSize(12f); // Đặt kích thước văn bản của trục Y bên trái

        YAxis rightAxis = memoryBarChart.getAxisRight();
        rightAxis.setTextSize(12f); // Đặt kích thước văn bản của trục Y bên phải


        Legend legend = memoryBarChart.getLegend();
        legend.setEnabled(false);

        Description description = new Description();
        description.setText("Memory Level of Words");
        description.setTextSize(12f); // Đặt kích thước văn bản là 12sp
        memoryBarChart.setDescription(description);
        memoryBarChart.invalidate(); // refresh
    }


}