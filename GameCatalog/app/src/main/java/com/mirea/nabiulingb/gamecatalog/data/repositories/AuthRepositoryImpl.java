package com.mirea.nabiulingb.gamecatalog.data.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.User;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    @Override
    public User login(String email, String password) {
        if ("test@test.com".equals(email) && "password".equals(password)) {
            return new User(1, "TestUser", email,
                    "https://example.com/avatar.jpg", null);
        }
        return null;
    }

    @Override
    public User loginWithOAuth(String provider, String token) {
        return new User(2, "OAuthUser", "oauth@test.com",
                "https://example.com/oauth_avatar.jpg",
                "steam".equals(provider) ? "76561197960287930" : null);
    }

    @Override
    public User register(String username, String email, String password) {
        return new User(3, username, email,
                "https://example.com/default_avatar.jpg", null);
    }
}
