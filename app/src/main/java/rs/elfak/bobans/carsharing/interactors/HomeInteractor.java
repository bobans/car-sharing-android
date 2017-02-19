package rs.elfak.bobans.carsharing.interactors;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.FirebaseToken;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class HomeInteractor extends BaseInteractor {

    public void unregisterFCM() {
        Observable<ResponseBody> response = ApiManager.getInstance().getApiMethods()
                .unregisterFCM(SessionManager.getInstance().getToken(), new FirebaseToken(SessionManager.getInstance().getFisebaseToken()));
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
