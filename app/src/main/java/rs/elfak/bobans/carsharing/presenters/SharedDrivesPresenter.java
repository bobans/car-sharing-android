package rs.elfak.bobans.carsharing.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.SharedDrivesInteractor;
import rs.elfak.bobans.carsharing.models.Filter;
import rs.elfak.bobans.carsharing.models.FilterDAO;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.ui.activities.CreateSharedDriveActivity;
import rs.elfak.bobans.carsharing.ui.activities.ViewSharedDriveActivity;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.ISharedDrivesView;
import rx.SingleSubscriber;

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
        String departure = null;
        String destination = null;
        if (SessionManager.getInstance().getUser() != null && SessionManager.getInstance().getUser().getFilter() != null) {
            Filter filter = SessionManager.getInstance().getUser().getFilter();
            departure = filter.getStart();
            destination = filter.getStop();
        }
        getInteractor().getSharedDrives(departure, destination,0, PAGE_SIZE, new SingleSubscriber<List<SharedDrive>>() {
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

    public void loadSharedDrives(int page) {
        int offset = page * PAGE_SIZE;
        String departure = null;
        String destination = null;
        if (SessionManager.getInstance().getUser() != null && SessionManager.getInstance().getUser().getFilter() != null) {
            Filter filter = SessionManager.getInstance().getUser().getFilter();
            departure = filter.getStart();
            destination = filter.getStop();
        }
        getInteractor().getSharedDrives(departure, destination, offset, PAGE_SIZE, new SingleSubscriber<List<SharedDrive>>() {
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

    public void onFilterChanged(@Nullable FilterDAO filter) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        if (filter != null) {
            getInteractor().saveFilter(filter, new SingleSubscriber<Filter>() {
                @Override
                public void onSuccess(Filter value) {
                    User user = SessionManager.getInstance().getUser();
                    user.setFilter(value);
                    SessionManager.getInstance().setUser(user);
                    if (isViewAttached()) {
                        getView().filterChanged();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (isViewAttached()) {
                        getView().showError(e, false);
                    }
                }
            });
        } else {
            getInteractor().clearFilter(new SingleSubscriber<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody value) {
                    User user = SessionManager.getInstance().getUser();
                    user.setFilter(null);
                    SessionManager.getInstance().setUser(user);
                    if (isViewAttached()) {
                        getView().filterChanged();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (isViewAttached()) {
                        getView().showError(e, false);
                    }
                }
            });
        }
    }
}
