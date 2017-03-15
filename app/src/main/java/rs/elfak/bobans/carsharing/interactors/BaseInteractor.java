package rs.elfak.bobans.carsharing.interactors;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BaseInteractor {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    protected <T> void subscribe(Observable<T> response, Observer<T> observer) {
        Subscription subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        manageSubscription(subscription);
    }

    protected <T> void subscribe(Single<T> response, SingleSubscriber<T> subscriber) {
        Subscription subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        manageSubscription(subscription);
    }

    protected void manageSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void onDestroy() {
        subscriptions.unsubscribe();
    }

}
