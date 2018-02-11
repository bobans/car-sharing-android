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

    public void login(SingleSubscriber<Token> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().login(), subscriber);
    }

    public void getUser(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(), subscriber);
    }

}
