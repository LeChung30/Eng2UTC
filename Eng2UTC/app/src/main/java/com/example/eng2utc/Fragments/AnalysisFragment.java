package com.example.eng2utc.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.eng2utc.Model.MarkOfTest;
import com.example.eng2utc.Model.MemoryLevel;
import com.example.eng2utc.R;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


import com.example.eng2utc.Retrofit.RetrofitController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalysisFragment extends Fragment {

    private BarChart memoryBarChart;
    private BarChart markOfTestBarChart;

    private TextView streaksTextView;
    private TextView studiedWordsTextView;
    //get id of the textview in the xml file at

    public AnalysisFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayStreaks();
        show_total_studied_words();
        display_bar_chart_memory();
        display_pie_chart_markoftest();

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
        markOfTestBarChart = view.findViewById(R.id.markoftestBarChart);
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

    private String get_today_date() {
        // Get the current date
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void displayStreaks() {
        //display streaks
        String userId = get_UID();
        String today = get_today_date();
        RetrofitController.getApiService().getStreak(userId, today).enqueue(new Callback<Integer>() {
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

    private String get_UID() {
        // Get the context of the fragment
        Context context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", null);
            if (userId != null) {
                return userId;
            } else {
                System.out.printf("Failed to get user id");
                return null;
            }
        } else {
            System.out.printf("Context is null");
            return null;
        }
    }

    private void show_total_studied_words() {

        //display total studied words
        String userId = get_UID();
        RetrofitController.getApiService().getTotalWords(userId).enqueue(new Callback<Integer>() {
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

    private void display_pie_chart_markoftest() {
        String userId = get_UID();
        // Initialize PieChart
        final ArrayList<MarkOfTest>[] markOfTests = new ArrayList[]{null};
        RetrofitController.getApiService().getMarkOfTest(userId).enqueue(new Callback<List<MarkOfTest>>() {
            @Override
            public void onResponse(Call<List<MarkOfTest>> call, Response<List<MarkOfTest>> response) {
                if (response.isSuccessful()) {
                    markOfTests[0] = new ArrayList<>(response.body());
                    setMarkOfTestBarChart(markOfTests[0]);
                } else {
                    System.out.printf("Failed to get mark of test");
                    //print error message
                    System.out.printf(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MarkOfTest>> call, Throwable t) {
                System.out.printf("Failed to get mark of test");
                //print error message
                System.out.printf(t.getMessage());
            }
        });
    }

    private void display_bar_chart_memory() {
        String userId = get_UID();
        // Initialize BarChart
        final ArrayList<MemoryLevel>[] memoryLevels = new ArrayList[]{null};
        RetrofitController.getApiService().getMemoryLevel(userId).enqueue(new Callback<List<MemoryLevel>>() {
            @Override
            public void onResponse(Call<List<MemoryLevel>> call, Response<List<MemoryLevel>> response) {
                if (response.isSuccessful()) {
                    memoryLevels[0] = new ArrayList<>(response.body());
                    setupBarChartMemory(memoryLevels[0]);
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

    private void setMarkOfTestBarChart(ArrayList<MarkOfTest> markOfTests) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();

        for (MarkOfTest markOfTest : markOfTests) {
            entries.add(new BarEntry(markOfTest.getMark(), markOfTest.getQuantity()));
            labels.add(markOfTest.getLabel());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Mark of Test");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);
        BarData data = new BarData(dataSet);
        markOfTestBarChart.setData(data);

        XAxis xAxis = markOfTestBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f); // Đảm bảo mỗi nhãn là một giá trị duy nhất
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        markOfTestBarChart.setExtraOffsets(10, 10, 10, 15); // Thêm khoảng trống xung quanh biểu đồ
        xAxis.setTextSize(10f); // Đặt kích thước văn bản của trục X

        YAxis leftAxis = markOfTestBarChart.getAxisLeft();
        leftAxis.setTextSize(12f);

        YAxis rightAxis = markOfTestBarChart.getAxisRight();
        rightAxis.setTextSize(12f);

        Legend legend = markOfTestBarChart.getLegend();
        legend.setEnabled(false);


        Description description = new Description();
        description.setText("Mark of Test");
        description.setTextSize(12f);
        markOfTestBarChart.setDescription(description);
        markOfTestBarChart.invalidate();
    }

    private void setupBarChartMemory(ArrayList<MemoryLevel> memoryLevels) {
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

        XAxis xAxis = memoryBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f); // Đảm bảo mỗi nhãn là một giá trị duy nhất
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        memoryBarChart.setExtraOffsets(10, 10, 10, 15); // Thêm khoảng trống xung quanh biểu đồ
        xAxis.setTextSize(10f); // Đặt kích thước văn bản của trục X

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