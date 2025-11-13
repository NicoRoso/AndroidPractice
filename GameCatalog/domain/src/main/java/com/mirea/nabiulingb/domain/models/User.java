package com.mirea.nabiulingb.domain.domain.models;

public class User {
    private final int id;
    private final String username;
    private final String email;
    private final String avatarUrl;
    private final String steamId;

    public User(int id, String username, String email, String avatarUrl, String steamId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.steamId = steamId;
    }

    public User(int id, String username, String email) {
        this(id, username, email, null, null);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getSteamId() { return steamId; }
}