package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.LoginEmailInteractor;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ILoginEmailView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailPresenter extends BasePresenter<ILoginEmailView, LoginEmailInteractor> {

    @NonNull
    @Override
    protected LoginEmailInteractor createInteractor() {
        return new LoginEmailInteractor();
    }

    public void login(String username, String password) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().login(username, password, new Observer<Token>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ApiError error = ApiManager.parseError(((HttpException) e).response());
                    switch (error.getCode()) {
                        case 401: {
                            if (isViewAttached()) {
                                getView().showWrongCredentials();
                                getView().showContent();
                            }
                            break;
                        }

                        default: {
                            if (isViewAttached()) {
                                getView().showError(e, false);
                                getView().showContent();
                            }
                            break;
                        }
                    }
                } else if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onNext(Token token) {
                SessionManager.getInstance().setToken(token.getToken());
                if (isViewAttached()) {
                    getView().showMain();
                }
            }
        });
    }
}
