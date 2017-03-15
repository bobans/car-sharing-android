package rs.elfak.bobans.carsharing.interactors.registration;

import retrofit2.Response;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserInteractor extends BaseInteractor {

    public void createUser(UserDAO user, SingleSubscriber<Response<Void>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().createUser(SessionManager.getInstance().getToken(), user), subscriber);
    }

    public void getUser(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(SessionManager.getInstance().getToken()), subscriber);
    }

}
