package rs.elfak.bobans.carsharing.interactors;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
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

public class CreateSharedDriveInteractor extends BaseInteractor {

    public void getCars(Observer<List<Car>> observer) {
        Observable<List<Car>> response = ApiManager.getInstance().getApiMethods().getCars(SessionManager.getInstance().getToken());
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void createSharedDrive(SharedDriveDAO sharedDrive, Observer<ResponseBody> observer) {
        Observable<ResponseBody> response = ApiManager.getInstance().getApiMethods().createSharedDrive(SessionManager.getInstance().getToken(), sharedDrive);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void updateSharedDrive(SharedDrive sharedDrive, Observer<ResponseBody> observer) {
        Observable<ResponseBody> response = ApiManager.getInstance().getApiMethods().updateSharedDrive(SessionManager.getInstance().getToken(), sharedDrive);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
