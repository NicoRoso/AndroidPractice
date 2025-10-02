package com.mirea.nabiulingb.gamecatalog.domain.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.User;

public interface AuthRepository {
    User login(String email, String password);
    User loginWithOAuth(String provider, String token);
    User register(String username, String email, String password);
}