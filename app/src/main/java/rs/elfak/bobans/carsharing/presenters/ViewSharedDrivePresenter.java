package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.views.IViewSharedDriveView;
import rx.Observer;

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

    public void loadSharedDrive() {
        if (isViewAttached() && sharedDrive != null) {
            getView().setData(sharedDrive);
        }
    }

    public void deleteSharedDrive() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().deleteSharedDrive(sharedDrive.getId(), new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, false);
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (isViewAttached()) {
                    getView().deleteSuccessful();
                    getView().showContent();
                }
            }
        });
    }

    public void onEditClick() {
        if (isViewAttached()) {
            Bundle extras = new Bundle();
            extras.putParcelable(CreateSharedDriveActivity.EXTRA_SHARED_DRIVE, sharedDrive);
            getView().navigateToActivityForResult(1, CreateSharedDriveActivity.class, extras);
        }
    }
}
