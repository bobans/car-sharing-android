package rs.elfak.bobans.carsharing.interactors;

import retrofit2.Response;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfileInteractor extends BaseInteractor {

    public void getMyProfile(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(SessionManager.getInstance().getToken()), subscriber);
    }

    public void updateUser(UserDAO user, SingleSubscriber<Response<Void>> subscriber) {
        // TODO update user on server
        subscriber.onSuccess(Response.<Void>success(null));
    }
}
