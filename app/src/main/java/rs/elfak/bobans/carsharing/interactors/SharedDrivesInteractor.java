package rs.elfak.bobans.carsharing.interactors;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Filter;
import rs.elfak.bobans.carsharing.models.FilterDAO;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesInteractor extends BaseInteractor {

    public void getSharedDrives(String departure, String destination, int offset, int limit, SingleSubscriber<List<SharedDrive>> subscriber) {
        if (departure != null && destination != null) {
            subscribe(ApiManager.getInstance().getApiMethods().getSharedDrives(departure, destination, offset, limit), subscriber);
        } else {
            subscribe(ApiManager.getInstance().getApiMethods().getSharedDrives(offset, limit), subscriber);
        }
    }

    public void saveFilter(FilterDAO filter, SingleSubscriber<Filter> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().saveFilter(filter), subscriber);
    }

    public void clearFilter(SingleSubscriber<ResponseBody> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().clearFilter(), subscriber);
    }

}
