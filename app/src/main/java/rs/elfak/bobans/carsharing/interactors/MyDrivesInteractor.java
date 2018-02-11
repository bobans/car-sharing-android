package rs.elfak.bobans.carsharing.interactors;

import java.util.List;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class MyDrivesInteractor extends BaseInteractor {

    public void getMyDrives(int offset, int limit, SingleSubscriber<List<SharedDrive>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getMyDrives(offset, limit), subscriber);
    }

}
