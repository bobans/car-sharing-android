package rs.elfak.bobans.carsharing.interactors;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ViewSharedDriveInteractor extends BaseInteractor {

    public void deleteSharedDrive(long id, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().deleteSharedDrive(SessionManager.getInstance().getToken(), id), subscriber);
    }

    public void requestARide(long id, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().requestARide(SessionManager.getInstance().getToken(), id), subscriber);
    }

    public void cancelRideRequest(long id, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().cancelRideRequest(SessionManager.getInstance().getToken(), id), subscriber);
    }

    public void updateRideRequest(long driveId, long passengerId, int status, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().updateRideRequest(SessionManager.getInstance().getToken(), driveId, passengerId, status), subscriber);
    }

}
