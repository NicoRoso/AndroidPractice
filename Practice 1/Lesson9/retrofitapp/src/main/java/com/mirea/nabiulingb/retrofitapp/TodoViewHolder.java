package com.mirea.nabiulingb.retrofitapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle;
    CheckBox checkBoxCompleted;
    ImageView imageViewTodo;
    TextView textViewUserId;
    private OnTodoCheckedListener checkedListener;
    private Todo currentTodo;

    public TodoViewHolder(@NonNull View itemView, OnTodoCheckedListener listener) {
        super(itemView);
        this.checkedListener = listener;

        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        imageViewTodo = itemView.findViewById(R.id.imageViewTodo);
        textViewUserId = itemView.findViewById(R.id.textViewUserId);

        checkBoxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (currentTodo != null && checkedListener != null) {
                    checkedListener.onTodoChecked(currentTodo, isChecked, getAdapterPosition());
                }
            }
        });
    }

    public void bind(Todo todo) {
        this.currentTodo = todo;
        textViewTitle.setText(todo.getTitle());
        textViewUserId.setText("User ID: " + todo.getUserId());

        loadImageWithResizeAndCrop(todo.getImageUrl());

        checkBoxCompleted.setOnCheckedChangeListener(null);
        checkBoxCompleted.setChecked(todo.getCompleted());

        checkBoxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (currentTodo != null && checkedListener != null) {
                    checkedListener.onTodoChecked(currentTodo, isChecked, getAdapterPosition());
                }
            }
        });

        if (todo.getCompleted()) {
            textViewTitle.setAlpha(0.6f);
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textViewTitle.setAlpha(1.0f);
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() & ~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public interface OnTodoCheckedListener {
        void onTodoChecked(Todo todo, boolean isChecked, int position);
    }

    private void loadImageBasic(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(imageViewTodo);
    }

    private void loadImageWithResizeAndCrop(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageViewTodo);
    }
}