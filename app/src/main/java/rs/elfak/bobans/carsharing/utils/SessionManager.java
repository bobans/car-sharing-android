package rs.elfak.bobans.carsharing.utils;

import rs.elfak.bobans.carsharing.models.User;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class SessionManager {

    public static final String KEY_TOKEN = "KEY_TOKEN";

    private static SessionManager instance;

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private String token;
    private User user;

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

    public void clearData() {
        token = null;
        user = null;
    }

}
