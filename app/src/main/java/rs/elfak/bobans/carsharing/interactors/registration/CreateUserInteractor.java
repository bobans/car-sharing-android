package rs.elfak.bobans.carsharing.interactors.registration;

import retrofit2.Response;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.User;
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

public class CreateUserInteractor extends BaseInteractor {

    public void createUser(User user, Observer<Response<Void>> observer) {
        Observable<Response<Void>> response = ApiManager.getInstance().getApiMethods().createUser(SessionManager.getInstance().getToken(), user);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
