package com.mirea.nabiulingb.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientInfoDataSource {
    private static final String PREF_NAME = "client_info_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    private final SharedPreferences sharedPreferences;

    public ClientInfoDataSource(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveClientInfo(String userId, String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "Гость");
    }

    public void clearClientInfo() {
        sharedPreferences.edit().clear().apply();
    }
}