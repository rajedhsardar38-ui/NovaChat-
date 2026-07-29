package com.novachat.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseHelper {
    private static final String PREFS_NAME = "nova_chat_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_NAME = "user_name";

    private final FirebaseAuth mAuth;
    private final SharedPreferences prefs;

    public FirebaseHelper(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null || prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean isLoggedIn, String phone, String name) {
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
                .putString(KEY_USER_PHONE, phone)
                .putString(KEY_USER_NAME, name)
                .apply();
    }

    public String getUserPhone() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getPhoneNumber() != null) {
            return user.getPhoneNumber();
        }
        return prefs.getString(KEY_USER_PHONE, "+1 555 0199");
    }

    public String getUserName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getDisplayName() != null && !user.getDisplayName().isEmpty()) {
            return user.getDisplayName();
        }
        return prefs.getString(KEY_USER_NAME, "Nova Explorer");
    }

    public void logout() {
        try {
            mAuth.signOut();
        } catch (Exception ignored) {}
        prefs.edit().clear().apply();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }
}
