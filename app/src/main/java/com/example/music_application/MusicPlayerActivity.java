package com.example.music_application;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_application.databinding.ActivityMusicPlayerBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    private Button playButton;
    private Button nextButton;
    private Button previousButton;
    private Button fastForwardButton;
    private Button fastPreviousButton;

    private TextView songName;
    private TextView startTime;
    private TextView endTime;

    private SeekBar timeDuration;

    private ImageView largeAlbumCover;

    private String sName;

    public static final String EXTRA_SONG_NAME = "Music Name";

    private MediaPlayer mediaPlayer = MusicPlayerInstance.getInstance();

    private int position;

    private ArrayList<AudioModel> localMusics;
    private AudioModel currentMusic;

    // Create a thread for update the music time seekbar
    // Thread updateSeekBar;





    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
     */


    private ActivityMusicPlayerBinding musicPlayerBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        /*
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         */


        // Initialize all relevant components;
        playButton = findViewById(R.id.playBtn);
        nextButton = findViewById(R.id.nextBtn);
        previousButton = findViewById(R.id.previousBtn);
        fastForwardButton = findViewById(R.id.fastForwardBtn);
        fastPreviousButton = findViewById(R.id.fastRewindBtn);

        songName = findViewById(R.id.textMusicName);

        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        timeDuration = findViewById(R.id.musicTime);

        largeAlbumCover = findViewById(R.id.largeAlbumCover);

        // Fetch All Music Data
        localMusics = (ArrayList<AudioModel>) getIntent().getSerializableExtra("MusicList");

        // Fetch info for current playing music
        getCurrentPlayingMusic();

        // set the seekbar update
        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    timeDuration.setProgress(mediaPlayer.getCurrentPosition());
                    startTime.setText(musicDurationTime(mediaPlayer.getCurrentPosition() + ""));

                    if(mediaPlayer.isPlaying()) {
                        playButton.setBackgroundResource(R.drawable.ic_pause_button);
                        mediaPlayer.pause();
                    } else {
                        playButton.setBackgroundResource(R.drawable.ic_play_button);
                        mediaPlayer.start();
                    }
                }

                // Create a handler for time update
                final Handler handler = new Handler();

                // update the time every second (1s = 1000ms)
                final int delay = 1000;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String currentTime = musicDurationTime(mediaPlayer.getCurrentPosition() + "");
                        startTime.setText(currentTime);
                        handler.postDelayed(this, delay);
                    }
                }, delay);
            }
        });

        timeDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Fetch info for current playing music
    private void getCurrentPlayingMusic() {
        currentMusic = localMusics.get(MusicPlayerInstance.currentIndex);
        songName.setText(currentMusic.getSongName());
        endTime.setText(musicDurationTime(currentMusic.getDuration()));

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAndPauseMusic();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMusic();
            }
        });

        fastPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastRewind();
            }
        });

        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastForward();
            }
        });

        playMusic();
    }

    // Change the text show for music time
    public String musicDurationTime(String duration) {
        Long durationInMillis = Long.parseLong(duration);
        // Set the format of music duration
        String actualTime = String.format("%02d:%02d",
                                          TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % TimeUnit.HOURS.toMinutes(1),
                                          TimeUnit.MILLISECONDS.toSeconds(durationInMillis) % TimeUnit.HOURS.toSeconds(1));

        return actualTime;
    }

    // set functions for all buttons
    private void playAndPauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    private void nextMusic() {
        if (MusicPlayerInstance.currentIndex == localMusics.size() - 1) {
            return;
        } else {
            MusicPlayerInstance.currentIndex = MusicPlayerInstance.currentIndex + 1;
        }
        mediaPlayer.reset();
        getCurrentPlayingMusic();
    }

    private void previousMusic() {
        if (MusicPlayerInstance.currentIndex == 0) {
            return;
        } else {
            MusicPlayerInstance.currentIndex = MusicPlayerInstance.currentIndex - 1;
        }
        mediaPlayer.reset();
        getCurrentPlayingMusic();
    }

    private void fastForward() {
        if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);   // fast forward 10 seconds while click on the button;
        }
    }

    private void fastRewind() {
        if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 10000) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);   // fast backward 10 seconds while click on the button;
        }
    }

    // make the logic for music play
    private void playMusic() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentMusic.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            timeDuration.setProgress(0);
            timeDuration.setMax(mediaPlayer.getDuration());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}