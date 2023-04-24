package com.asem.musicapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongsHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    ImageView iconImageView;
    public SongsHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.music_title_text);
        iconImageView = itemView.findViewById(R.id.icon_view);
    }
}
