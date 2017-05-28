package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rs.elfak.bobans.carsharing.interactors.GiveFeedbackInteractor;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.IGiveFeedbackView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class GiveFeedbackPresenter extends BasePresenter<IGiveFeedbackView, GiveFeedbackInteractor> {

    private SharedDrive sharedDrive;

    public GiveFeedbackPresenter(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
    }

    @NonNull
    @Override
    protected GiveFeedbackInteractor createInteractor() {
        return new GiveFeedbackInteractor();
    }

    public void loadData() {
        if (isViewAttached() && sharedDrive != null) {
            boolean isOwner = sharedDrive.getUser().getId() == SessionManager.getInstance().getUser().getId();
            getView().setOwner(isOwner, sharedDrive.getUser(), sharedDrive.getCar());
            List<Passenger> passengers = new ArrayList<>();
            for (Passenger passenger : sharedDrive.getPassengers()) {
                if (passenger.getUser().getId() != SessionManager.getInstance().getUser().getId()) {
                    passengers.add(passenger);
                }
            }
            getView().setPassengers(passengers);
        }
    }
}
