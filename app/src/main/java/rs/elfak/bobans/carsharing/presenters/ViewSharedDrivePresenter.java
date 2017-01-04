package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.views.IViewSharedDriveView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ViewSharedDrivePresenter extends BasePresenter<IViewSharedDriveView, ViewSharedDriveInteractor> {

    private SharedDrive sharedDrive;

    public ViewSharedDrivePresenter(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
    }

    @NonNull
    @Override
    protected ViewSharedDriveInteractor createInteractor() {
        return new ViewSharedDriveInteractor();
    }

}
