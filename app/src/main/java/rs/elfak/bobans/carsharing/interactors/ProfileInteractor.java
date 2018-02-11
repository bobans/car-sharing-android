package rs.elfak.bobans.carsharing.interactors;

import okhttp3.MultipartBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.UploadPhotoResponse;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfileInteractor extends BaseInteractor {

    public void getMyProfile(SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getUser(), subscriber);
    }

    public void updateUser(UserDAO user, SingleSubscriber<User> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().updateUser(user), subscriber);
    }

    public void uploadPhoto(MultipartBody.Part body, SingleSubscriber<UploadPhotoResponse> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().uploadPhoto(body), subscriber);
    }

    public void deleteAccount(SingleSubscriber<Void> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().deleteUser(), subscriber);
    }

}
