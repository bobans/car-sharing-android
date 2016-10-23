package rs.elfak.bobans.carsharing.presenters.registration;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.views.registration.ICreateUserView;

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
}
