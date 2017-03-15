package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.List;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.CreateSharedDriveInteractor;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
import rs.elfak.bobans.carsharing.views.ICreateSharedDriveView;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateSharedDrivePresenter extends BasePresenter<ICreateSharedDriveView, CreateSharedDriveInteractor> {

    private static final int REQUEST_DATE = 9001;
    private static final int REQUEST_DEPARTURE_TIME = 9002;
    private static final int REQUEST_ARRIVAL_TIME = 9003;

    private SharedDrive sharedDrive;

    public CreateSharedDrivePresenter(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
    }

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
        getInteractor().getCars(new SingleSubscriber<List<Car>>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(e, pullToRefresh);
                }
            }

            @Override
            public void onSuccess(List<Car> cars) {
                if (isViewAttached()) {
                    Car selected = null;
                    if (sharedDrive != null) {
                        selected = sharedDrive.getCar();
                    }
                    getView().setCars(cars, selected);
                    getView().showContent();
                }
            }
        });
    }

    private void createDrive(SharedDriveDAO sharedDrive) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().createSharedDrive(sharedDrive, new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().sharedDriveCreated();
                }
            }
        });
    }

    private void updateDrive(final SharedDrive sharedDrive) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().updateSharedDrive(sharedDrive, new SingleSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().sharedDriveUpdated(sharedDrive);
                }
            }
        });
    }

    public void onDoneClicked(SharedDriveDAO sharedDrive) {
        if (this.sharedDrive != null) {
            this.sharedDrive.setDeparture(sharedDrive.getDeparture());
            this.sharedDrive.setDestination(sharedDrive.getDestination());
            this.sharedDrive.setStops(sharedDrive.getStops());
            this.sharedDrive.setCar(sharedDrive.getCar());
            this.sharedDrive.setSeats(sharedDrive.getSeats());
            this.sharedDrive.getPreferences().setMusic(sharedDrive.getPreferences().getMusic());
            this.sharedDrive.getPreferences().setTalk(sharedDrive.getPreferences().getTalk());
            this.sharedDrive.getPreferences().setPets(sharedDrive.getPreferences().getPets());
            this.sharedDrive.getPreferences().setSmoking(sharedDrive.getPreferences().getSmoking());
            this.sharedDrive.getTime().setDate(sharedDrive.getTime().getDate());
            this.sharedDrive.getTime().setRepeat(sharedDrive.getTime().isRepeat());
            this.sharedDrive.getTime().setRepeatDays(sharedDrive.getTime().getRepeatDays());
            this.sharedDrive.getTime().setDepartureTime(sharedDrive.getTime().getDepartureTime());
            this.sharedDrive.getTime().setArrivalTime(sharedDrive.getTime().getArrivalTime());
            this.sharedDrive.getPrice().setPrice(sharedDrive.getPrice().getPrice());
            this.sharedDrive.getPrice().setType(sharedDrive.getPrice().getType());
            updateDrive(this.sharedDrive);
        } else {
            createDrive(sharedDrive);
        }
    }

    public void loadSharedDrive() {
        if (sharedDrive != null && isViewAttached()) {
            getView().setData(sharedDrive);
        }
    }
}
