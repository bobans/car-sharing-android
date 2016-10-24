package rs.elfak.bobans.carsharing.interactors;

import android.util.Base64;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.models.User;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailInteractor extends BaseInteractor {

    public void login(String username, String password, Observer<Token> observer) {
        String token = username + ":" + password;
        token = "Basic " + Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
        Observable<Token> response = ApiManager.getInstance().getApiMethods().login(token);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getUser(String token, Observer<User> observer) {
        Observable<User> response = ApiManager.getInstance().getApiMethods().getUser(token);
        response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
