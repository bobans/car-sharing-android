package rs.elfak.bobans.carsharing.interactors;

import java.util.List;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesInteractor extends BaseInteractor {

    public void getSharedDrives(int offset, int limit, Observer<List<SharedDrive>> observer) {
        Observable<List<SharedDrive>> response = ApiManager.getInstance().getApiMethods().getSharedDrives(SessionManager.getInstance().getToken(), offset, limit);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
