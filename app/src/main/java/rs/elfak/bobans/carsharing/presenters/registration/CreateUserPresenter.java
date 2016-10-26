package rs.elfak.bobans.carsharing.presenters.registration;

import android.support.annotation.NonNull;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
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

    public void createUser(User user) {
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
                if (isViewAttached()) {
                    getView().showMain();
                }
            }
        });
    }
}
