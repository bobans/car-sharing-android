package rs.elfak.bobans.carsharing.views;

import org.joda.time.DateTime;

import java.util.List;

import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.SharedDrive;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ICreateSharedDriveView extends IBaseView<SharedDrive> {
    /**
     * Shows date picker.
     *
     * @param requestCode
     * @param initialDate
     */
    void showDatePicker(int requestCode, DateTime initialDate);

    /**
     * Shows date.
     *
     * @param date
     */
    void setDate(DateTime date);

    /**
     * Shows time picker.
     *
     * @param requestCode
     * @param initialTime
     */
    void showTimePicker(int requestCode, DateTime initialTime);

    /**
     * Shows departure time.
     *
     * @param time
     */
    void setDepartureTime(DateTime time);

    /**
     * Shows arrival time.
     *
     * @param time
     */
    void setArrivalTime(DateTime time);

    /**
     * Sets user's cars.
     *
     * @param cars
     * @param selected
     */
    void setCars(List<Car> cars, Car selected);

    /**
     * Shared drive is successfully created.
     */
    void sharedDriveCreated();

    /**
     * Shared drive is successfully updated.
     */
    void sharedDriveUpdated(SharedDrive sharedDrive);
}
