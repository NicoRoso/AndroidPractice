package com.mirea.nabiulingb.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<HistoricalEvent> {

    private Context context;
    private int resource;

    public EventAdapter(Context context, int resource, List<HistoricalEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HistoricalEvent event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView titleView = convertView.findViewById(R.id.textViewTitle);
        TextView yearView = convertView.findViewById(R.id.textViewYear);
        TextView descriptionView = convertView.findViewById(R.id.textViewDescription);

        if (event != null) {
            titleView.setText(event.getTitle());
            yearView.setText("Год: " + event.getYear());
            descriptionView.setText(event.getDescription());

            try {
                String imageName = event.getImageName();
                int resId = context.getResources().getIdentifier(
                        imageName,
                        "drawable",
                        context.getPackageName()
                );

                if (resId != 0) {
                    imageView.setImageResource(resId);
                } else {
                    imageView.setImageResource(R.drawable.ic_launcher_foreground);
                }
            } catch (Exception e) {
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }

        return convertView;
    }
}