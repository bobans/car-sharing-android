package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import rs.elfak.bobans.carsharing.interactors.MyDrivesInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.ui.activities.ViewSharedDriveActivity;
import rs.elfak.bobans.carsharing.views.IMyDrivesView;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class MyDrivesPresenter extends BasePresenter<IMyDrivesView, MyDrivesInteractor> {

    private static final int PAGE_SIZE = 20;

    @NonNull
    @Override
    protected MyDrivesInteractor createInteractor() {
        return new MyDrivesInteractor();
    }

    public void loadMyDrives(final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }
        getInteractor().getMyDrives(0, PAGE_SIZE, new SingleSubscriber<List<SharedDrive>>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, pullToRefresh);
                    getView().showContent();
                }
            }

            @Override
            public void onSuccess(List<SharedDrive> sharedDrives) {
                if (isViewAttached()) {
                    getView().setData(sharedDrives);
                    getView().showContent();
                }
            }
        });
    }

    public void loadMyDrives(int page) {
        int offset = page * PAGE_SIZE;
        getInteractor().getMyDrives(offset, PAGE_SIZE, new SingleSubscriber<List<SharedDrive>>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                }
            }

            @Override
            public void onSuccess(List<SharedDrive> sharedDrives) {
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
