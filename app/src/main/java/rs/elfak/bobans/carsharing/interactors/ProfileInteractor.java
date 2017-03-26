package rs.elfak.bobans.carsharing.interactors;

import okhttp3.MultipartBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.UploadPhotoResponse;
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

    public void updateUser(UserDAO user, SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().updateUser(SessionManager.getInstance().getToken(), user), subscriber);
    }

    public void uploadPhoto(MultipartBody.Part body, SingleSubscriber<UploadPhotoResponse> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().uploadPhoto(SessionManager.getInstance().getToken(), body), subscriber);
    }
}
