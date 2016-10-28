package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.HomeInteractor;
import rs.elfak.bobans.carsharing.views.IHomeView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class HomePresenter extends BasePresenter<IHomeView, HomeInteractor> {

    @NonNull
    @Override
    protected HomeInteractor createInteractor() {
        return new HomeInteractor();
    }

}
