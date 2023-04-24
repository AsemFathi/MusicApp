package com.asem.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsHolder> implements RecyclerViewInterface {
    Context context;
    ArrayList<AudioModel> songList;

    public SongsAdapter(Context context, ArrayList<AudioModel> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item , parent , false);
        return new SongsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsHolder holder, int position) {
        AudioModel songData =songList.get(position);
        holder.titleTextView.setText(songData.getTitle());

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
