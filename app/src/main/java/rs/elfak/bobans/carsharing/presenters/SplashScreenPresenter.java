package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.SplashScreenInteractor;
import rs.elfak.bobans.carsharing.views.ISplashScreenView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SplashScreenPresenter extends AbstractPresenter<ISplashScreenView, SplashScreenInteractor> {

    @NonNull
    @Override
    SplashScreenInteractor createInteractor() {
        return new SplashScreenInteractor();
    }

}
