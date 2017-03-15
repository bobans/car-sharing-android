package rs.elfak.bobans.carsharing.interactors;

import com.google.firebase.iid.FirebaseInstanceId;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class HomeInteractor extends BaseInteractor {

    public void unregisterFCM() {
        ApiManager.getInstance().getApiMethods()
                .unregisterFCM(SessionManager.getInstance().getToken(), FirebaseInstanceId.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
