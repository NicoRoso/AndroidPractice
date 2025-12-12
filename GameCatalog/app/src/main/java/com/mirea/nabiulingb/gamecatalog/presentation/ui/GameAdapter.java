package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.R;
import com.squareup.picasso.Picasso;

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
        private final ImageView ivGameImage;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvGameTitle);
            tvGenre = itemView.findViewById(R.id.tvGameGenre);
            ivGameImage = itemView.findViewById(R.id.ivGameImage);
        }

        public void bind(Game game) {
            tvTitle.setText(game.getTitle());

            // --- ИСПРАВЛЕННАЯ ЛОГИКА ДЛЯ ЖАНРА ---
            String genre = game.getGenre();
            String displayGenre = (genre != null && !genre.trim().isEmpty())
                    ? genre
                    : "Неизвестный"; // Отображаем "Неизвестный" вместо null

            tvGenre.setText(String.format("Жанр: %s | Рейтинг: %.1f", displayGenre, game.getRating()));
            // ------------------------------------

            String imageUrl = game.getImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.loading_placeholder)
                        .error(R.drawable.error_placeholder)
                        .into(ivGameImage);
            } else {
                ivGameImage.setImageResource(R.drawable.no_image_available);
            }
        }
    }
}