package rs.elfak.bobans.carsharing.interactors;

import android.util.Base64;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailInteractor extends BaseInteractor {

    public void login(String username, String password, SingleSubscriber<Token> subscriber) {
        String token = username + ":" + password;
        token = "Basic " + Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
        SessionManager.getInstance().setToken(token);
        subscribe(ApiManager.getInstance().getApiMethods().login(), subscriber);
    }

    public void getUser(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(), subscriber);
    }

}
