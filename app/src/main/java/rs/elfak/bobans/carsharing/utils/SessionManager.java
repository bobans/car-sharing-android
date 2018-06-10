package rs.elfak.bobans.carsharing.utils;

import com.google.gson.reflect.TypeToken;

import rs.elfak.bobans.carsharing.models.User;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class SessionManager {

    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_USER = "KEY_USER";

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
        if (user == null) {
            String userJson = SharedPreferencesUtils.getInstance().getPrefString(KEY_USER);
            if (userJson != null) {
                user = CarSharingApplication.getInstance().getGson().fromJson(userJson, new TypeToken<User>() {}.getType());
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        SharedPreferencesUtils.getInstance().putPrefString(KEY_USER, CarSharingApplication.getInstance().getGson().toJson(user));
    }

    public void clearData() {
        token = null;
        user = null;

        SharedPreferencesUtils.getInstance().deletePref(KEY_TOKEN);
        SharedPreferencesUtils.getInstance().deletePref(KEY_USER);
    }

}
