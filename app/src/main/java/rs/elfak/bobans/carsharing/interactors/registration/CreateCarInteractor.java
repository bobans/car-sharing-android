package rs.elfak.bobans.carsharing.interactors.registration;

import java.util.List;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
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

public class CreateCarInteractor extends BaseInteractor {

    public void getMakes(Observer<List<Make>> observer) {
        Observable<List<Make>> response = ApiManager.getInstance().getApiMethods().getMakes(SessionManager.getInstance().getToken());
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getModels(long makeId, Observer<List<Model>> observer) {
        Observable<List<Model>> response = ApiManager.getInstance().getApiMethods().getModels(SessionManager.getInstance().getToken(), makeId);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
