package rs.elfak.bobans.carsharing.interactors;

import com.google.firebase.iid.FirebaseInstanceId;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class HomeInteractor extends BaseInteractor {

    public void unregisterFCM() {
        Subscription subscription = ApiManager.getInstance().getApiMethods()
                .unregisterFCM(FirebaseInstanceId.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        manageSubscription(subscription);
    }

    public void getSharedDrive(long driveId, SingleSubscriber<SharedDrive> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getSharedDrive(driveId), subscriber);
    }
}
