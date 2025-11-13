package com.mirea.nabiulingb.data.repositories;

import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.WishlistItem;
import com.mirea.nabiulingb.domain.repositories.WishlistRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistRepositoryImpl implements WishlistRepository {

    private final List<WishlistItem> testWishlist = createTestWishlist();

    @Override
    public List<WishlistItem> getWishlist(int userId) {
        List<WishlistItem> userWishlist = new ArrayList<>();
        for (WishlistItem item : testWishlist) {
            userWishlist.add(item);
        }
        return userWishlist;
    }

    @Override
    public boolean addToWishlist(int userId, int gameId) {
        return true;
    }

    @Override
    public boolean removeFromWishlist(int userId, int gameId) {
        return true;
    }

    @Override
    public Map<Integer, Integer> checkDiscounts(int userId) {
        Map<Integer, Integer> discounts = new HashMap<>();
        discounts.put(2, 15);
        discounts.put(4, 25);
        return discounts;
    }

    private List<WishlistItem> createTestWishlist() {
        List<WishlistItem> wishlist = new ArrayList<>();

        Game cyberpunk = new Game(2, "Cyberpunk 2077",
                "Open-world RPG", "RPG", "PC", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", 59.99, null);

        Game tlou2 = new Game(4, "The Last of Us Part II",
                "Action-adventure survival horror", "Action",
                "PS5", "2020-06-19", 9.0,
                "https://example.com/tlou2.jpg", 69.99, null);

        Game batman = new Game(6, "Batman: Arkham Knight",
                "Action-adventure Batman game", "Action", "PC, PS4", "2015-06-23", 9.2,
                "https://example.com/batman_arkham_knight.jpg", 29.99, 40);

        wishlist.add(new WishlistItem(1, cyberpunk, "2024-01-01"));
        wishlist.add(new WishlistItem(2, tlou2, "2024-01-05"));
        wishlist.add(new WishlistItem(3, batman, "2024-01-10"));

        return wishlist;
    }
}