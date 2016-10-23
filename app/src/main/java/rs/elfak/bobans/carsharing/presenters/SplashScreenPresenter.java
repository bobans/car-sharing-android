package rs.elfak.bobans.carsharing.presenters;

import android.os.Handler;
import android.support.annotation.NonNull;

import retrofit2.adapter.rxjava.HttpException;
import rs.elfak.bobans.carsharing.api.ApiError;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.SplashScreenInteractor;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.utils.Constants;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ISplashScreenView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SplashScreenPresenter extends BasePresenter<ISplashScreenView, SplashScreenInteractor> {

    private long startTime;

    @NonNull
    @Override
    protected SplashScreenInteractor createInteractor() {
        return new SplashScreenInteractor();
    }

    public void login() {
        startTime = System.currentTimeMillis();
        String authentication = SessionManager.getInstance().getToken();
        getInteractor().login(authentication, new Observer<Token>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ApiError error = ApiManager.parseError(((HttpException) e).response());
                    switch (error.getCode()) {
                        case 401: {
                            delayAndShowLogin();
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
//                    getView().showError(e, false);
//                    getView().showContent();
                    // TODO
                    delayAndShowLogin();
                }
            }

            @Override
            public void onNext(Token token) {
                SessionManager.getInstance().setToken(token.getToken());
                delayAndShowMain();
            }
        });
    }

    private void delayAndShowLogin() {
        long duration = System.currentTimeMillis() - startTime;
        if (duration < Constants.SPLASH_SCREEN_MINIMAL_DURATION) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isViewAttached()) {
                        getView().showLogin();
                    }
                }
            }, Constants.SPLASH_SCREEN_MINIMAL_DURATION - duration);
        } else if (isViewAttached()) {
            getView().showLogin();
        }
    }

    private void delayAndShowMain() {
        long duration = System.currentTimeMillis() - startTime;
        if (duration < Constants.SPLASH_SCREEN_MINIMAL_DURATION) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isViewAttached()) {
                        getView().showMain();
                    }
                }
            }, Constants.SPLASH_SCREEN_MINIMAL_DURATION - duration);
        } else if (isViewAttached()) {
            getView().showMain();
        }
    }

}
