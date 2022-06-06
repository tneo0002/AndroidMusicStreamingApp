package com.example.music_application;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.music_application.databinding.FragmentPieChartBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class PieChartFragment extends Fragment {
    private FragmentPieChartBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPieChartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();



        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(0.2f, "Monday"));
        pieEntries.add(new PieEntry(0.05f, "Tuesday"));
        pieEntries.add(new PieEntry(0.15f, "Wednesday"));
        pieEntries.add(new PieEntry(0.1f, "Thursday"));
        pieEntries.add(new PieEntry(0.2f, "Friday"));
        pieEntries.add(new PieEntry(0.1f, "Saturday"));
        pieEntries.add(new PieEntry(0.1f, "Sunday"));

//        private void addDataSet(PieChart chart) {
//            ArrayList<PieEntry> yEntries = new ArrayList<>();
//            ArrayList<String> xEntries = new ArrayList<>();
//
//            ArrayList<Float> yData = new ArrayList<>(Arrays.asList(25.3f, 10.6f, 66.76f, 44.32f, 46.10f, 16.89f, 23.9f);
//            ArrayList<String> xData = new ArrayList<>(Arrays.asList("Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat");
//
//            for (int idx = 0; idx < yData.size(); idx++) {
//                yEntries.add(new PieEntry(yData.get(idx), idx));
//            }
//
//            for (int idx = 1; idx < xData.size(); idx++) {
//                xEntries.add(new PieEntry(xData.get(idx)));
//            }


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IPieDataSet> dataSets = new ArrayList<>();

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(binding.pieChart));
        pieData.setValueTextSize(18f);
        pieData.setValueTextColor(Color.WHITE);

//        Legend legend = binding.pieChart.getLegend();
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//
//        legend.setDrawInside(false);
//        legend.setWordWrapEnabled(true);

        binding.pieChart.setData(pieData);
        binding.pieChart.setVisibility(View.VISIBLE);
        binding.pieChart.animateY(1300);
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.setEntryLabelTextSize(13);
        binding.pieChart.setRotationEnabled(true);
        binding.pieChart.setHoleRadius(40f);
        binding.pieChart.setTransparentCircleAlpha(5);
        binding.pieChart.setCenterText("Usage on Day");
        binding.pieChart.setCenterTextSize(20);
        binding.pieChart.getDescription().setEnabled(false);
        //binding.pieChart.setDrawEntryLabels(false);
        binding.pieChart.getLegend().setEnabled(false);


        //refresh the chart
        binding.pieChart.invalidate();

        return view;


        //List<String> xAxisValues = new ArrayList<>(Arrays.asList("Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"));
        //binding.pieChart.ser(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        //PieData pieData = new PieData(pieDataSet);
        //binding.pieChart.setData(pieData);

        //binding.pieChart.setVisibility(View.VISIBLE);
        //binding.pieChart.animateY(4000);
        //description will be displayed as "Description Label" if not provided
        //Description description = new Description();
        //description.setText("Search by Day");
        //binding.pieChart.setDescription(description);

        //refresh the chart
        //binding.pieChart.invalidate();

        //return view;
    }
}