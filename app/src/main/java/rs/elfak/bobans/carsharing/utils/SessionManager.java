package rs.elfak.bobans.carsharing.utils;

import rs.elfak.bobans.carsharing.models.User;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SessionManager {

    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_FIREBASE_ID = "KEY_FIREBASE_ID";

    private static SessionManager instance;

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private String token;
    private User user;
    private String fisebaseToken;

    private SessionManager() {

    }

    public void setToken(String token) {
        this. token = token;
        SharedPreferencesUtils.getInstance().putPrefString(KEY_TOKEN, token);
    }

    public String getToken() {
        if (token == null) {
            token = SharedPreferencesUtils.getInstance().getPrefString(KEY_TOKEN);
        }
        return token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFisebaseToken() {
        if (fisebaseToken == null) {
            fisebaseToken = SharedPreferencesUtils.getInstance().getPrefString(KEY_FIREBASE_ID);
        }
        return fisebaseToken;
    }

    public void setFisebaseToken(String fisebaseToken) {
        this.fisebaseToken = fisebaseToken;
    }

    public void clearData() {
        token = null;
        user = null;
        fisebaseToken = null;
    }

}
