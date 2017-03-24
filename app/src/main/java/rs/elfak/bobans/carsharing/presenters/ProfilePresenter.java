package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.ProfileInteractor;
import rs.elfak.bobans.carsharing.views.IProfileView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfilePresenter extends BasePresenter<IProfileView, ProfileInteractor> {

    @NonNull
    @Override
    protected ProfileInteractor createInteractor() {
        return new ProfileInteractor();
    }

}
