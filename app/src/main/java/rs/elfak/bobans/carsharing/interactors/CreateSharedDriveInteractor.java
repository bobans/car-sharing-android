package rs.elfak.bobans.carsharing.interactors;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateSharedDriveInteractor extends BaseInteractor {

    public void getCars(SingleSubscriber<List<Car>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getCars(), subscriber);
    }

    public void createSharedDrive(SharedDriveDAO sharedDrive, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().createSharedDrive(sharedDrive), subscriber);
    }

    public void updateSharedDrive(SharedDrive sharedDrive, SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().updateSharedDrive(sharedDrive), subscriber);
    }

}
