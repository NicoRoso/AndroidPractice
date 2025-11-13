package com.mirea.nabiulingb.domain.domain.usecases.auth;

import com.mirea.nabiulingb.domain.domain.models.User;
import com.mirea.nabiulingb.domain.domain.repositories.AuthRepository;

public class RegisterUser {
    private final AuthRepository authRepository;

    public RegisterUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User execute(String username, String email, String password) {
        return authRepository.register(username, email, password);
    }
}