package com.mirea.nabiulingb.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private Context context;
    private List<Todo> todoList;
    private TodoViewHolder.OnTodoCheckedListener checkedListener;

    public TodoAdapter(Context context, List<Todo> todoList, TodoViewHolder.OnTodoCheckedListener listener) {
        this.context = context;
        this.todoList = todoList;
        this.checkedListener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view, checkedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    public void updateData(List<Todo> newTodos) {
        this.todoList = newTodos;
        notifyDataSetChanged();
    }

    public void updateItem(int position, Todo updatedTodo) {
        if (position >= 0 && position < todoList.size()) {
            todoList.set(position, updatedTodo);
            notifyItemChanged(position);
        }
    }
}