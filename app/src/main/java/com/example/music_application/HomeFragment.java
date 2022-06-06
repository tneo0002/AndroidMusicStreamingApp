package com.example.music_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.music_application.Worker.Ass3Worker;
import com.example.music_application.adapter.RvAdapter;
import com.example.music_application.api.AuthApi;
import com.example.music_application.api.SpotifyApi;
import com.example.music_application.databinding.FragmentHomeBinding;
import com.example.music_application.model.TrackInfo;
import com.example.music_application.retrofit.AccessToken;
import com.example.music_application.retrofit.RecommendResponse;
import com.example.music_application.retrofit.RetrofitClient;
import com.example.music_application.retrofit.Tracks1;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    private ArrayList<TrackInfo> trackInfoList;
    private RecyclerView.LayoutManager layoutManager;
    private RvAdapter adapter;
    private FragmentHomeBinding binding;
    private AuthApi tokenApi;
    private SpotifyApi spotifyApi;
    private String accept;
    private String content;
    private String token;
    private String seedArtists;
    private String seedGenres;
    private String seedTracks;
    private List<Tracks1> hits;

    private static final String TAG = "ASS3Worker";

    private static final String CLIENT_ID_SECRET = "a64850863fca4a4eb0213f01f4e734cd:3d279b8761a74757b7e7001d0e55df56";
    private static final String GRANT_TYPE = "client_credentials";
    private static final String AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString(CLIENT_ID_SECRET.getBytes());
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        trackInfoList = new ArrayList<>();
        adapter = new RvAdapter(getActivity(), trackInfoList);
        binding.RecrV.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        binding.RecrV.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.RecrV.setLayoutManager(layoutManager);

        // Create the data that you would like to pass to the work manager
        Data data = new Data.Builder().putInt("number", 10).build();

        // Create the constraints for the work manager
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        // Create a one time work Request for Work Manager
        OneTimeWorkRequest requestOnce = new OneTimeWorkRequest.Builder(Ass3Worker.class)
                .setInputData(data)
                .addTag("Once")
                .build();

        // Run the One Time Work
        WorkManager.getInstance().enqueue(requestOnce);

        // Create the Time Consuming Work - repeatInterval means how often the work will be done
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                                                      .Builder(Ass3Worker.class, 1, TimeUnit.DAYS)
                                                      .setInputData(data)
                                                      .setConstraints(constraints)
                                                      .setInitialDelay(10, TimeUnit.HOURS)
                                                      .addTag("Periodic")
                                                      .build();
        // Start the Periodic Work
        WorkManager.getInstance().enqueue(periodicWorkRequest);

        // Log the periodic work
        WorkManager.getInstance(getContext()).getWorkInfosByTagLiveData("Periodic").observe(getViewLifecycleOwner(), new Observer<List<WorkInfo>>() {
            Date currentTime = Calendar.getInstance().getTime();
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for(WorkInfo workInfo : workInfos) {
                    Log.d(currentTime + TAG, "Periodic Request: Current State: " + workInfo.getState());
                }
            }
        });

        //spotifyApi = RetrofitClient.getRetrofitService();
        tokenApi = RetrofitClient.getRetrofitToken();
        spotifyApi = RetrofitClient.getRetrofitService();

        accept = "application/json";
        content = "application/json";
        token = "Bearer ";
        seedArtists = "0f8NhxVK9EDVZ3czeZw87X";
        seedGenres = "pop";
        seedTracks  = "0LaC5zGWYPHndZGol44zsI";


        if (trackInfoList.size() > 0 ) {
            trackInfoList.clear();
            adapter.setTrackInfoList(trackInfoList);
        }

        Call<AccessToken> callForToken = tokenApi.getToken(AUTHORIZATION, CONTENT_TYPE, GRANT_TYPE);
        callForToken.enqueue(new Callback<AccessToken>() {

            @Override
            public void onResponse(Call<AccessToken> tokenCall, Response<AccessToken> tokenResponse) {
                //Toast.makeText(getActivity(), tokenResponse.body().toString(), Toast.LENGTH_SHORT).show();
                if (tokenResponse.isSuccessful()) {
                    //binding.error.setText(tokenResponse.body().getAccessToken());
                    token += tokenResponse.body().getAccessToken();
                    //Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
                    Call<RecommendResponse>callRecommend = spotifyApi.customRecommend(accept, content, token, seedArtists, seedGenres, seedTracks);

                    //Toast.makeText(getActivity(), tokenResponse.body().toString(), Toast.LENGTH_SHORT).show();
                    //makes an async request & invokes callback methods when the response returns
                    callRecommend.enqueue(new Callback<RecommendResponse>() {

                        @Override
                        public void onResponse(Call<RecommendResponse> call, Response<RecommendResponse> recommendResponse) {
                            //Toast.makeText(getActivity(), recommendResponse.body().toString(), Toast.LENGTH_SHORT).show();
                            if (recommendResponse.isSuccessful()) {
                                //Toast.makeText(getActivity(), "recommendResponse Obtained", Toast.LENGTH_SHORT).show();
                                hits = recommendResponse.body().getTracks();

                                for (Tracks1 hit : hits) {
                                    String albumName = hit.getAlbum().getName();
                                    String albumArtist = hit.getArtists().get(0).getName();
                                    String albumImage = hit.getAlbum().getImages().get(1).getUrl();
                                    saveData(albumName, albumArtist, albumImage);
                                }
                                adapter.setTrackInfoList(trackInfoList);
                            }
                            else {
                                Toast.makeText(getActivity(), "No Recommend found.", Toast.LENGTH_SHORT).show();
                                Log.i("Error ","Response failed");
                            }
                        }
                        @Override
                        public void onFailure(Call<RecommendResponse> call, Throwable t){
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Authorization unsuccessful!", Toast.LENGTH_SHORT).show();
                    Log.i("Error ", "Authorization Failed");
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle loginBundle = getActivity().getIntent().getExtras();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtras(loginBundle);
                startActivity(intent);
            }
        });

        binding.statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GraphActivity.class);
                Bundle loginBundle = getActivity().getIntent().getExtras();
                intent.putExtras(loginBundle);
                startActivity(intent);
            }
        });

        // Create the action for the show Work Manager show
        binding.workMgrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(requestOnce);
                AlertDialog.Builder notiBuilder = new AlertDialog.Builder(getContext());

                // When User click outside the dialogue area, the dialogue will disappear
                notiBuilder.setCancelable(true);

                // Set the Title and Message for detailed information
                notiBuilder.setTitle("Android Version Notification");
                String message = "You are currently working on Android 12";
                notiBuilder.setMessage(message);

                notiBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                notiBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.AlertNoti.setVisibility(View.VISIBLE);
                    }
                });
                notiBuilder.show();

                // Log the One-Time work
                WorkManager.getInstance(getContext()).getWorkInfosByTagLiveData("Once").observe(getViewLifecycleOwner(), new Observer<List<WorkInfo>>() {
                    Date currentTime = Calendar.getInstance().getTime();
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        for (WorkInfo workInfo : workInfos) {
                            Log.d(currentTime + TAG, "Instant Request: Current state: " + workInfo.getState());
                        }
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void saveData(String name, String artist, String url) {
        TrackInfo trackInfo = new TrackInfo(name, artist, url);
        trackInfoList.add(trackInfo);
    }
}