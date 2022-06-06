package com.example.music_application;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MusicPlaying extends AppCompatActivity {

    // Create Music List
    private RecyclerView musicListRV;
    private TextView noMusicTV;
    private ArrayList<AudioModel> songList = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        // Show the music list
        // musicList = findViewById(R.id.musicList);

        musicListRV = findViewById(R.id.musicRecyclerView);
        noMusicTV = findViewById(R.id.noSongsText);
        backBtn = findViewById(R.id.back_main_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle loginBundle02 = getIntent().getExtras();
                Intent intent = new Intent(MusicPlaying.this, MainActivity.class);
                intent.putExtras(loginBundle02);
                startActivity(intent);
            }
        });

        // Show the permission authenticator and let user to allow the permission
        if (checkPermission() == false) {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

        // Use a cursor to fetch all music from the local device's storage
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);

        // Fetch Audio metadata


        // Move the cursor to fetch audio metadata
        while(cursor.moveToNext()) {
            AudioModel musicData = new AudioModel(cursor.getString(2), cursor.getString(0), cursor.getString(1), cursor.getString(3));
            // Check whether the music exists or not and the audio file is not a hidden file, if both are not, add it;
            if (new File(musicData.getPath()).exists()) {
                // add fetched song metadata to List
                songList.add(musicData);
            }
        }

        // If there are NO audio files fetched, then show the default UI
        if(songList.size() == 0) {
            noMusicTV.setVisibility(View.VISIBLE);
        } else {
            // If audio data fetched, then show the music list
            musicListRV.setLayoutManager(new LinearLayoutManager(this));
            musicListRV.setAdapter(new MusicListAdapter(songList, getApplicationContext()));
        }




    }

    // Check the permission for get file from internal storage;
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MusicPlaying.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // If do not have the permission, then request permission
    private void requestPermission() {
        // if user deny the permission, then send some message to let them allow the critical permission;
        if (ActivityCompat.shouldShowRequestPermissionRationale(MusicPlaying.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MusicPlaying.this, "This perssion is a necessary permission, please make sure you have allowed it.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MusicPlaying.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 666);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(musicListRV != null){
            musicListRV.setAdapter(new MusicListAdapter(songList, getApplicationContext()));
        }
    }
}