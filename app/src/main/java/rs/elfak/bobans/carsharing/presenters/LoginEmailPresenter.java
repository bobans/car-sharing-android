package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.LoginEmailInteractor;
import rs.elfak.bobans.carsharing.views.ILoginEmailView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class LoginEmailPresenter extends AbstractPresenter<ILoginEmailView, LoginEmailInteractor> {

    @NonNull
    @Override
    LoginEmailInteractor createInteractor() {
        return new LoginEmailInteractor();
    }

}
