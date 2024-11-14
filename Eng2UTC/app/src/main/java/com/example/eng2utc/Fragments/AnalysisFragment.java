package com.example.eng2utc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;

import com.example.eng2utc.R;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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

    private LineChart lineChart;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        // Initialize LineChart
        lineChart = view.findViewById(R.id.lineChart);
        setupLineChart();

        // Initialize PieChart
        PieChart pieChart = view.findViewById(R.id.pieChart);
        setupPieChart(pieChart);

        // Initialize BarChart
        BarChart barChart = view.findViewById(R.id.barChart);
        setupBarChart(barChart);

        // Initialize RadarChart
        RadarChart radarChart = view.findViewById(R.id.radarChart);
        setupRadarChart(radarChart);

        // Initialize BubbleChart
        BubbleChart bubbleChart = view.findViewById(R.id.bubbleChart);
        setupBubbleChart(bubbleChart);

        // Initialize ScatterChart
        ScatterChart scatterChart = view.findViewById(R.id.scatterChart);
        setupScatterChart(scatterChart);

        return view;
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

    //create a method to draw a line chart
    private void setupLineChart() {
        // Cấu hình LineChart
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        // Tạo dữ liệu mẫu
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 1));
        values.add(new Entry(1, 2));
        values.add(new Entry(2, 3));
        values.add(new Entry(3, 4));
        values.add(new Entry(4, 5));

        LineDataSet set1 = new LineDataSet(values, "Sample Data");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);
        set1.setLineWidth(1.5f);
        set1.setCircleColor(Color.RED);
        set1.setCircleRadius(5f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.BLACK);

        LineData data = new LineData(set1);
        lineChart.setData(data);
    }

    private void setupPieChart(PieChart pieChart) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Category 1"));
        entries.add(new PieEntry(20f, "Category 2"));
        entries.add(new PieEntry(50f, "Category 3"));

        PieDataSet dataSet = new PieDataSet(entries, "Sample Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(20f); // Set the text size of the labels to be larger

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f); // Set the text size of the values to be larger
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    private void setupBarChart(BarChart barChart) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 40));
        entries.add(new BarEntry(2, 30));
        entries.add(new BarEntry(3, 60));
        entries.add(new BarEntry(4, 50));
        entries.add(new BarEntry(5, 70));

        BarDataSet dataSet = new BarDataSet(entries, "Sample Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate(); // refresh
    }

    private void setupRadarChart(RadarChart radarChart) {
        ArrayList<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(40));
        entries.add(new RadarEntry(30));
        entries.add(new RadarEntry(60));
        entries.add(new RadarEntry(50));
        entries.add(new RadarEntry(70));

        RadarDataSet dataSet = new RadarDataSet(entries, "Sample Data");
        dataSet.setColor(Color.BLUE);
        RadarData data = new RadarData(dataSet);
        radarChart.setData(data);
        radarChart.invalidate(); // refresh
    }

    private void setupBubbleChart(BubbleChart bubbleChart) {
        ArrayList<BubbleEntry> entries = new ArrayList<>();
        entries.add(new BubbleEntry(1, 40, 10));
        entries.add(new BubbleEntry(2, 30, 20));
        entries.add(new BubbleEntry(3, 60, 30));
        entries.add(new BubbleEntry(4, 50, 40));
        entries.add(new BubbleEntry(5, 70, 50));

        BubbleDataSet dataSet = new BubbleDataSet(entries, "Sample Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BubbleData data = new BubbleData(dataSet);
        bubbleChart.setData(data);
        bubbleChart.invalidate(); // refresh
    }

    private void setupCandleStickChart(CandleStickChart candleStickChart) {
        ArrayList<CandleEntry> entries = new ArrayList<>();
        entries.add(new CandleEntry(1, 40, 10, 30, 20));
        entries.add(new CandleEntry(2, 30, 20, 40, 10));
        entries.add(new CandleEntry(3, 60, 30, 50, 40));
        entries.add(new CandleEntry(4, 50, 40, 70, 30));
        entries.add(new CandleEntry(5, 70, 50, 80, 60));

        CandleDataSet dataSet = new CandleDataSet(entries, "Sample Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        CandleData data = new CandleData(dataSet);
        candleStickChart.setData(data);
        candleStickChart.invalidate(); // refresh
    }

    private void setupScatterChart(ScatterChart scatterChart) {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 40));
        entries.add(new Entry(2, 30));
        entries.add(new Entry(3, 60));
        entries.add(new Entry(4, 50));
        entries.add(new Entry(5, 70));

        ScatterDataSet dataSet = new ScatterDataSet(entries, "Sample Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ScatterData data = new ScatterData(dataSet);
        scatterChart.setData(data);
        scatterChart.invalidate(); // refresh
    }

    private void setupCombinedChart(CombinedChart combinedChart) {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, 40));
        lineEntries.add(new Entry(2, 30));
        lineEntries.add(new Entry(3, 60));
        lineEntries.add(new Entry(4, 50));
        lineEntries.add(new Entry(5, 70));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Sample Data");
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.BLACK);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 40));
        barEntries.add(new BarEntry(2, 30));
        barEntries.add(new BarEntry(3, 60));
        barEntries.add(new BarEntry(4, 50));
        barEntries.add(new BarEntry(5, 70));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Sample Data");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        CombinedData data = new CombinedData();
        data.setData(new LineData(lineDataSet));
        data.setData(new BarData(barDataSet));
        combinedChart.setData(data);
        combinedChart.invalidate(); // refresh
    }

}