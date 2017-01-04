package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.CreateSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
import rs.elfak.bobans.carsharing.views.ICreateSharedDriveView;
import rx.Observer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateSharedDrivePresenter extends BasePresenter<ICreateSharedDriveView, CreateSharedDriveInteractor> {

    private static final int REQUEST_DATE = 9001;
    private static final int REQUEST_DEPARTURE_TIME = 9002;
    private static final int REQUEST_ARRIVAL_TIME = 9003;

    @NonNull
    @Override
    protected CreateSharedDriveInteractor createInteractor() {
        return new CreateSharedDriveInteractor();
    }

    public void onDateClicked(DateTime date) {
        if (isViewAttached()) {
            DateTime initialDate = date;
            if (initialDate == null) {
                initialDate = DateTime.now();
            }
            getView().showDatePicker(REQUEST_DATE, initialDate);
        }
    }

    public void onDatePicked(int requestCode, DateTime datePicked) {
        switch (requestCode) {
            case REQUEST_DATE: {
                if (isViewAttached()) {
                    getView().setDate(datePicked);
                }
            }
        }
    }

    public void onDepartureTimeClicked(DateTime time) {
        if (isViewAttached()) {
            DateTime initialTime = time;
            if (time == null) {
                initialTime = DateTime.now().withTimeAtStartOfDay();
            }
            getView().showTimePicker(REQUEST_DEPARTURE_TIME, initialTime);
        }
    }

    public void onTimePicked(int requestCode, int hourOfDay, int minute) {
        DateTime time = DateTime.now().withHourOfDay(hourOfDay).withMinuteOfHour(minute);
        switch (requestCode) {
            case REQUEST_DEPARTURE_TIME: {
                if (isViewAttached()) {
                    getView().setDepartureTime(time);
                }
            }

            case REQUEST_ARRIVAL_TIME: {
                if (isViewAttached()) {
                    getView().setArrivalTime(time);
                }
            }
        }
    }

    public void onArrivalTimeClicked(DateTime time) {
        if (isViewAttached()) {
            DateTime initialTime = time;
            if (initialTime == null) {
                initialTime = DateTime.now().withTimeAtStartOfDay();
            }
            getView().showTimePicker(REQUEST_ARRIVAL_TIME, initialTime);
        }
    }

    public void loadCars(final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }
        getInteractor().getCars(new Observer<List<Car>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, pullToRefresh);
                }
            }

            @Override
            public void onNext(List<Car> cars) {
                if (isViewAttached()) {
                    getView().setCars(cars);
                    getView().showContent();
                }
            }
        });
    }

    public void createDrive(SharedDriveDAO sharedDrive) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().createSharedDrive(sharedDrive, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().sharedDriveCreated();
                }
            }
        });
    }
}
