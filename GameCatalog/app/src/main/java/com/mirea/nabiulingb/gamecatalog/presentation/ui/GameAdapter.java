package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList = Collections.emptyList();

    public void setGameList(List<Game> newGameList) {
        this.gameList = newGameList != null ? newGameList : Collections.emptyList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.bind(gameList.get(position));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvGenre;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvGameTitle);
            tvGenre = itemView.findViewById(R.id.tvGameGenre);
        }

        public void bind(Game game) {
            tvTitle.setText(game.getTitle());
            tvGenre.setText(String.format("Жанр: %s | Рейтинг: %.1f", game.getGenre(), game.getRating()));
        }
    }
}