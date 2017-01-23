package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.PassengerDAO;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.utils.SessionManager;
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

    public void loadData() {
        if (isViewAttached() && sharedDrive != null) {
            getView().setData(sharedDrive);
            getView().setIsOwner(sharedDrive.getUser().getId() == SessionManager.getInstance().getUser().getId());
            boolean isPassenger = false;
            for (Passenger passenger : sharedDrive.getPassengers()) {
                if (passenger.getUser().getId() == SessionManager.getInstance().getUser().getId()) {
                    isPassenger = true;
                }
            }
            getView().setIsPassenger(isPassenger);
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

    public void requestARide() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().requestARide(sharedDrive.getId(), new Observer<ResponseBody>() {

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
                    getView().requestSuccessful();
                    getView().showContent();
                }
            }
        });
    }

    public void cancelRideRequest() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().cancelRideRequest(sharedDrive.getId(), new Observer<ResponseBody>() {

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
                    getView().requestCanceled();
                    getView().showContent();
                }
            }
        });
    }

    public void updateRideRequest(final int adapterPosition, long passengerId, final int status) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().updateRideRequest(sharedDrive.getId(), passengerId, status, new Observer<ResponseBody>() {
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
                    getView().requestUpdated(adapterPosition, status);
                    getView().showContent();
                }
            }
        });
    }

}
