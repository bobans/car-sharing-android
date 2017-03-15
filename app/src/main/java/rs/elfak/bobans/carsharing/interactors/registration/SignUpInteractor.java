package rs.elfak.bobans.carsharing.interactors.registration;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.Registration;
import rs.elfak.bobans.carsharing.models.Token;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SignUpInteractor extends BaseInteractor {
    public void register(Registration registration, SingleSubscriber<Token> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().register(registration), subscriber);
    }
}
