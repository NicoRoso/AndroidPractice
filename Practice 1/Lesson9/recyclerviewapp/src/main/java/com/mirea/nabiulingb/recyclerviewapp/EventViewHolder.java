package com.mirea.nabiulingb.recyclerviewapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView titleView;
    private TextView yearView;
    private TextView descriptionView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.textViewTitle);
        yearView = itemView.findViewById(R.id.textViewYear);
        descriptionView = itemView.findViewById(R.id.textViewDescription);
    }

    public void bind(HistoricalEvent event, Context context) {
        titleView.setText(event.getTitle());
        yearView.setText(String.valueOf(event.getYear()));
        descriptionView.setText(event.getDescription());

        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(event.getImageName(), "drawable", pkgName);
        if (resID != 0) {
            imageView.setImageResource(resID);
        }
    }
}