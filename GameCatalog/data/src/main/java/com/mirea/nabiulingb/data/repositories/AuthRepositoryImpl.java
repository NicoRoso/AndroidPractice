package com.mirea.nabiulingb.data.repositories;

import android.content.Context;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mirea.nabiulingb.data.local.ClientInfoDataSource;
import com.mirea.nabiulingb.domain.models.User;
import com.mirea.nabiulingb.domain.repositories.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final ClientInfoDataSource clientInfoDataSource;

    public AuthRepositoryImpl(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.clientInfoDataSource = new ClientInfoDataSource(context);
    }

    @Override
    public User login(String email, String password) {
        try {
            AuthResult authResult = Tasks.await(
                    firebaseAuth.signInWithEmailAndPassword(email, password)
            );
            FirebaseUser firebaseUser = authResult.getUser();

            if (firebaseUser != null) {
                clientInfoDataSource.saveClientInfo(
                        firebaseUser.getUid(),
                        firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "User"
                );
            }
            return mapFirebaseUserToUser(firebaseUser);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User loginWithOAuth(String provider, String token) {
        return null;
    }

    @Override
    public User register(String username, String email, String password) {
        try {
            AuthResult authResult = Tasks.await(
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
            );

            FirebaseUser firebaseUser = authResult.getUser();
            if (firebaseUser != null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();
                Tasks.await(firebaseUser.updateProfile(profileUpdates));

                clientInfoDataSource.saveClientInfo(
                        firebaseUser.getUid(),
                        username
                );

                return mapFirebaseUserToUser(firebaseUser);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void logout() {
        firebaseAuth.signOut();
        clientInfoDataSource.clearClientInfo();
    }

    public String getCurrentUserId() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    private User mapFirebaseUserToUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;

        int domainId = firebaseUser.getUid().hashCode();

        return new User(
                domainId,
                firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "User",
                firebaseUser.getEmail(),
                firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null,
                null
        );
    }
}