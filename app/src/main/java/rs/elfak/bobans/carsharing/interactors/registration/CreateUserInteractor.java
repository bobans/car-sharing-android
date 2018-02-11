package rs.elfak.bobans.carsharing.interactors.registration;

import retrofit2.Response;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserInteractor extends BaseInteractor {

    public void createUser(UserDAO user, SingleSubscriber<Response<Void>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().createUser(user), subscriber);
    }

    public void getUser(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(), subscriber);
    }

}
