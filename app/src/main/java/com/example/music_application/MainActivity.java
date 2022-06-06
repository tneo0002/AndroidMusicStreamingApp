package com.example.music_application;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.music_application.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();



    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = item -> {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SecondFragment()).commit();
                break;
            case R.id.musicPlay:
                Bundle loginBundle02 = getIntent().getExtras();
                Intent intent = new Intent(this, MusicPlaying.class);
                intent.putExtras(loginBundle02);
                startActivity(intent);
                break;
            case R.id.mapShow:
                Bundle loginBundle = getIntent().getExtras();
                Intent intent02 = new Intent(this, MapActivity.class);
                intent02.putExtras(loginBundle);
                startActivity(intent02);
                break;
        }
        return true;
    };
}