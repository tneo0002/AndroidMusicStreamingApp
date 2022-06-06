package com.example.music_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_application.databinding.ActivityGraphBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class GraphActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ActivityGraphBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        binding = ActivityGraphBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bottomNavigationView = findViewById(R.id.bottomNav1);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, new PieChartFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = item -> {
        switch (item.getItemId()){
            case R.id.barChart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, new BarChartFragment()).commit();
                break;
            case R.id.pieChart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, new PieChartFragment()).commit();
                break;
            case R.id.back_to_main:
                Bundle loginBundle02 = getIntent().getExtras();
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                intent.putExtras(loginBundle02);
                startActivity(intent);
        }
        return true;
    };
}
