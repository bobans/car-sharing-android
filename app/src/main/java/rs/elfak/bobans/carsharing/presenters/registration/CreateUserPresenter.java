package rs.elfak.bobans.carsharing.presenters.registration;

import android.support.annotation.NonNull;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.registration.ICreateUserView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserPresenter extends BasePresenter<ICreateUserView, CreateUserInteractor> {
    @NonNull
    @Override
    protected CreateUserInteractor createInteractor() {
        return new CreateUserInteractor();
    }

    public void createUser(final UserDAO user) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().createUser(user, new Observer<Response<Void>>() {
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
                                getView().showUserAlreadyExists();
                                getView().showContent();
                            }
                            break;
                        }

                        default: {
                            if (isViewAttached()) {
                                getView().showError(e, false);
                                getView().showContent();
                            }
                        }
                    }
                } else {
                    if (isViewAttached()) {
                        getView().showError(e, false);
                        getView().showContent();
                    }
                }
            }

            @Override
            public void onNext(Response<Void> voidResponse) {
                switch (voidResponse.code()) {
                    case 201: {
                        getUser();
                        if (isViewAttached()) {
                            getView().showContent();
                            getView().showMain();
                        }
                        break;
                    }
                    case 409: {
                        if (isViewAttached()) {
                            getView().showUserAlreadyExists();
                            getView().showContent();
                        }
                        break;
                    }

                    default: {
                        if (isViewAttached()) {
                            getView().showError(new Exception(), false);
                            getView().showContent();
                        }
                    }
                }
            }
        });
    }

    private void getUser() {
        getInteractor().getUser(new Observer<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ApiError error = ApiManager.parseError(((HttpException) e).response());
                    switch (error.getCode()) {
                        case 404: {
                            if (isViewAttached()) {
                                getView().showError(e, false);
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
                } else {
                    if (isViewAttached()) {
                        getView().showError(e, false);
                        getView().showContent();
                    }
                }
            }

            @Override
            public void onNext(User user) {
                if (user != null) {
                    SessionManager.getInstance().setUser(user);
                    if (isViewAttached()) {
                        getView().showContent();
                        getView().showMain();
                    }
                } else {
                    if (isViewAttached()) {
                        getView().showContent();
                        getView().showError(new Exception("User null"), false);
                    }
                }
            }
        });
    }

}
