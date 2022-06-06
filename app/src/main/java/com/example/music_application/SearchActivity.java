package com.example.music_application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.music_application.adapter.RvAdapter;
import com.example.music_application.api.AuthApi;
import com.example.music_application.api.SpotifyApi;
import com.example.music_application.databinding.ActivitySearchBinding;
import com.example.music_application.databinding.RvCardBinding;
import com.example.music_application.model.TrackInfo;
import com.example.music_application.retrofit.AccessToken;
import com.example.music_application.retrofit.RetrofitClient;
import com.example.music_application.retrofit.SearchResponse;
import com.example.music_application.retrofit.Tracks;
import com.example.music_application.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TrackInfo> trackInfoList;
    private RvAdapter adapter;

    private static final String CLIENT_ID_SECRET = "a64850863fca4a4eb0213f01f4e734cd:3d279b8761a74757b7e7001d0e55df56";
    private static final String GRANT_TYPE = "client_credentials";
    private static final String AUTHORIZATION = "Basic " + Base64.getEncoder().encodeToString(CLIENT_ID_SECRET.getBytes());
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    private String accept;
    private String content;
    private String token;
    private String searchKeyword;
    private String searchType;
    private int limit;
    private SpotifyApi spotifyApi;
    private AuthApi tokenApi;
    private List<Tracks.Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        trackInfoList = new ArrayList<>();
        adapter = new RvAdapter(this, trackInfoList);
        binding.rV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.rV.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.rV.setLayoutManager(layoutManager);
        spotifyApi = RetrofitClient.getRetrofitService();
        tokenApi = RetrofitClient.getRetrofitToken();
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                accept = "application/json";
                content = "application/json";
                token = "Bearer ";
                searchKeyword = binding.editText.getText().toString().trim();
                searchType = "track";
                limit = 40;

                if (!searchKeyword.isEmpty() || trackInfoList.size() > 0 ) {
                    trackInfoList.clear();
                    adapter.setTrackInfoList(trackInfoList);
                }

                Call<AccessToken> callForToken = tokenApi.getToken(AUTHORIZATION, CONTENT_TYPE, GRANT_TYPE);
                callForToken.enqueue(new Callback<AccessToken>() {

                    @Override
                    public void onResponse(Call<AccessToken> tokenCall, Response<AccessToken> tokenResponse) {
                        if (tokenResponse.isSuccessful()) {
                            token += tokenResponse.body().getAccessToken();
                            Call<SearchResponse> callToSearch = spotifyApi.getInfo(accept, content, token, searchKeyword, searchType, limit);
                            callToSearch.enqueue(new Callback<SearchResponse>() {
                                public void onResponse(Call<SearchResponse> searchCall, Response<SearchResponse> searchResponse) {
                                    if (searchResponse.isSuccessful()) {
                                        items = searchResponse.body().getTracks().getItems();
                                        for (Tracks.Item item : items) {
                                            String name = item.getName();
                                            String artist = item.getArtists().get(0).getName();
                                            String imageUrl = item.getAlbum().getImages().get(1).getUrl();
                                            saveData(name, artist, imageUrl);
                                        }
                                        adapter.setTrackInfoList(trackInfoList);
                                    } else {
                                        Toast.makeText(SearchActivity.this, "No match found!\nTry different keywords.", Toast.LENGTH_SHORT).show();
                                        Log.i("Error", "Search Failed");
                                    }
                                }

                                public void onFailure(Call<SearchResponse> searchCall, Throwable t) {
                                    Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(SearchActivity.this, "Authorization unsuccessful!", Toast.LENGTH_SHORT).show();
                            Log.i("Error ", "Authorization Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle loginBundle = getIntent().getExtras();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtras(loginBundle);
                startActivity(intent);
            }
        });

        binding.player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent (SearchActivity.this, MusicActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void saveData(String name, String artist, String imageUrl) {
        TrackInfo trackInfo = new TrackInfo(name, artist, imageUrl);
        trackInfoList.add(trackInfo);
    }
}