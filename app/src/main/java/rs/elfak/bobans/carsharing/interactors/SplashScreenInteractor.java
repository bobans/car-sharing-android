package rs.elfak.bobans.carsharing.interactors;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.models.User;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SplashScreenInteractor extends BaseInteractor {

    public void login(String token, SingleSubscriber<Token> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().login(token), subscriber);
    }

    public void getUser(String token, SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(token), subscriber);
    }

}
