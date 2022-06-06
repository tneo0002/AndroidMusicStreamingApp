package com.example.music_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.music_application.databinding.FragmentBarChartBinding;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class BarChartFragment extends Fragment {
    private FragmentBarChartBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentBarChartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 6766));
        barEntries.add(new BarEntry(1, 4444));
        barEntries.add(new BarEntry(2, 2222));
        barEntries.add(new BarEntry(3, 5555));
        barEntries.add(new BarEntry(4, 1111));
        barEntries.add(new BarEntry(5, 3656));
        barEntries.add(new BarEntry(6, 3435));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Steps");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"));
        binding.barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        barData.setBarWidth(1.0f);
        binding.barChart.setVisibility(View.VISIBLE);
        binding.barChart.animateY(1300);
        //description will be displayed as "Description Label" if not provided
        Description description = new Description();
        description.setText("Usage on day");
        binding.barChart.setDescription(description);

        //refresh the chart
        binding.barChart.invalidate();

        return view;
    }
}