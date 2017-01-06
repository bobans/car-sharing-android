package rs.elfak.bobans.carsharing.interactors;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
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
public class ViewSharedDriveInteractor extends BaseInteractor {

    public void deleteSharedDrive(long id, Observer<ResponseBody> observer) {
        Observable<ResponseBody> response = ApiManager.getInstance().getApiMethods().deleteSharedDrive(SessionManager.getInstance().getToken(), id);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
