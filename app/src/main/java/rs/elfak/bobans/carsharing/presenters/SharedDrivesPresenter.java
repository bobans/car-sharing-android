package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import rs.elfak.bobans.carsharing.interactors.SharedDrivesInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.ui.activities.ViewSharedDriveActivity;
import rs.elfak.bobans.carsharing.views.ISharedDrivesView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrivesPresenter extends BasePresenter<ISharedDrivesView, SharedDrivesInteractor> {

    private static final int PAGE_SIZE = 20;

    @NonNull
    @Override
    protected SharedDrivesInteractor createInteractor() {
        return new SharedDrivesInteractor();
    }

    public void loadSharedDrives(final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }
        getInteractor().getSharedDrives(0, PAGE_SIZE, new Observer<List<SharedDrive>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, pullToRefresh);
                    getView().showContent();
                }
            }

            @Override
            public void onNext(List<SharedDrive> sharedDrives) {
                if (isViewAttached()) {
                    getView().setData(sharedDrives);
                    getView().showContent();
                }
            }
        });
    }

    public void loadSharedDrives(int page) {
        int offset = page * PAGE_SIZE;
        getInteractor().getSharedDrives(offset, PAGE_SIZE, new Observer<List<SharedDrive>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                }
            }

            @Override
            public void onNext(List<SharedDrive> sharedDrives) {
                if (isViewAttached()) {
                    getView().setData(sharedDrives);
                }
            }
        });
    }

    public void onSharedDriveClick(SharedDrive sharedDrive) {
        if (isViewAttached()) {
            Bundle extras = new Bundle();
            extras.putParcelable(ViewSharedDriveActivity.EXTRA_SHARED_DRIVE, sharedDrive);
            getView().navigateToActivityForResult(1, ViewSharedDriveActivity.class, extras);
        }
    }

    public void onCreateDriveClick() {
        if (isViewAttached()) {
            getView().navigateToActivityForResult(1, CreateSharedDriveActivity.class, null);
        }
    }
}
