package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.ViewSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.ui.activities.GiveFeedbackActivity;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.IViewSharedDriveView;
import rx.SingleSubscriber;

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
            boolean isOwner = sharedDrive.getUser().getId() == SessionManager.getInstance().getUser().getId();
            DateTime time = sharedDrive.getTime().getDate();
            DateTime departureTime = sharedDrive.getTime().getDepartureTime();
            time = time.withTime(departureTime.getHourOfDay(), departureTime.getMinuteOfHour(), departureTime.getSecondOfMinute(), departureTime.getMillisOfSecond());
            boolean isFromPast = time.isBeforeNow();
            // TODO repeatable drive is from past ???
            getView().setIsOwner(isOwner, isFromPast);
            boolean isPassenger = false;
            for (Passenger passenger : sharedDrive.getPassengers()) {
                if (passenger.getUser().getId() == SessionManager.getInstance().getUser().getId()) {
                    isPassenger = true;
                }
            }
            getView().setIsPassenger(isPassenger, isFromPast);
        }
    }

    public void deleteSharedDrive() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().deleteSharedDrive(sharedDrive.getId(), new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, false);
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
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
        getInteractor().requestARide(sharedDrive.getId(), new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, false);
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
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
        getInteractor().cancelRideRequest(sharedDrive.getId(), new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, false);
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
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
        getInteractor().updateRideRequest(sharedDrive.getId(), passengerId, status, new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, false);
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                if (isViewAttached()) {
                    getView().requestUpdated(adapterPosition, status);
                    getView().showContent();
                }
            }
        });
    }

    public void onGiveFeedback() {
        Bundle extras = new Bundle();
        extras.putParcelable(GiveFeedbackActivity.EXTRA_SHARED_DRIVE, sharedDrive);
        if (isViewAttached()) {
            getView().navigateToActivity(GiveFeedbackActivity.class, extras);
        }
    }

    public void onEditSharedDrive(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
        if (isViewAttached()) {
            getView().setData(sharedDrive);
        }
    }
}
