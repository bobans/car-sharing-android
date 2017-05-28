package rs.elfak.bobans.carsharing.views;

import java.util.List;

import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.Passenger;
import rs.elfak.bobans.carsharing.models.User;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public interface IGiveFeedbackView extends IBaseView<Object> {
    void setOwner(boolean isOwner, User user, Car car);
    void setPassengers(List<Passenger> passengers);
}
