package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.interactors.HomeInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.ViewSharedDriveActivity;
import rs.elfak.bobans.carsharing.views.IHomeView;
import rx.SingleSubscriber;

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

    public void unregisterFCM(String token) {
        getInteractor().unregisterFCM(token);
    }

    public void loadDrive(long driveId) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().getSharedDrive(driveId, new SingleSubscriber<SharedDrive>(){
            @Override
            public void onSuccess(SharedDrive value) {
                if (isViewAttached()) {
                    getView().showContent();
                    Bundle extras = new Bundle();
                    extras.putParcelable(ViewSharedDriveActivity.EXTRA_SHARED_DRIVE, value);
                    getView().navigateToActivityForResult(1, ViewSharedDriveActivity.class, extras);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(error, false);
                }
            }
        });
    }
}
