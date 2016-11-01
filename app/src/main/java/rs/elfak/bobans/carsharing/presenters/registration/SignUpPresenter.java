package rs.elfak.bobans.carsharing.presenters.registration;

import android.support.annotation.NonNull;

import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.registration.SignUpInteractor;
import rs.elfak.bobans.carsharing.models.Registration;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.registration.ISignUpView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SignUpPresenter extends BasePresenter<ISignUpView, SignUpInteractor> {

    @NonNull
    @Override
    protected SignUpInteractor createInteractor() {
        return new SignUpInteractor();
    }

    public void register(String username, String password) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        Registration registration = new Registration(username, password);
        getInteractor().register(registration, new Observer<Token>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ApiError error = ApiManager.parseError(((HttpException) e).response());
                    switch (error.getCode()) {
                        case 409: {
                            if (isViewAttached()) {
                                getView().showAlreadyExists();
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
                    getView().showContent();
                    getView().showCreateUser();
                }
            }
        });
    }
}
