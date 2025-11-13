package com.mirea.nabiulingb.domain.domain.repositories;

import com.mirea.nabiulingb.domain.domain.models.User;

public interface AuthRepository {
    User login(String email, String password);
    User loginWithOAuth(String provider, String token);
    User register(String username, String email, String password);
}