package com.example.music_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_application.databinding.RvCardBinding;
import com.example.music_application.model.TrackInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private static ArrayList<TrackInfo> trackInfoList = new ArrayList<>();
    private Context context;

    public RvAdapter(Context context, ArrayList<TrackInfo> trackInfoList) {
        this.trackInfoList = trackInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvCardBinding binding = RvCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder viewHolder, int position) {
        viewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Music Page
            }
        });
        viewHolder.binding.rvName.setText(trackInfoList.get(position).getName());
        viewHolder.binding.rvArtist.setText(trackInfoList.get(position).getArtist());
        String uri = trackInfoList.get(position).getImageUrl();
        Picasso.with(context).load(uri).into(viewHolder.binding.rvImage);
    }

    @Override
    public int getItemCount() {
        return trackInfoList.size();
    }

    public void setTrackInfoList(ArrayList<TrackInfo> trackInfoList) {
        RvAdapter.trackInfoList = trackInfoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvCardBinding binding;

        public ViewHolder(RvCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}