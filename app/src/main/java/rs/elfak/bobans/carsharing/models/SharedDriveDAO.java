package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SharedDriveDAO implements Parcelable {

    private User user;
    private Car car;
    private String departure;
    private String destination;
    private List<String> stops;
    private DrivePreferencesDAO preferences;
    private DriveTimeDAO time;
    private DrivePriceDAO price;
    private int seats;
    private List<Passenger> passengers;

    public SharedDriveDAO() {
    }

    protected SharedDriveDAO(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        car = in.readParcelable(Car.class.getClassLoader());
        departure = in.readString();
        destination = in.readString();
        stops = in.createStringArrayList();
        preferences = in.readParcelable(DrivePreferences.class.getClassLoader());
        time = in.readParcelable(DriveTime.class.getClassLoader());
        price = in.readParcelable(DrivePrice.class.getClassLoader());
        seats = in.readInt();
        passengers = in.createTypedArrayList(Passenger.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(car, flags);
        dest.writeString(departure);
        dest.writeString(destination);
        dest.writeStringList(stops);
        dest.writeParcelable(preferences, flags);
        dest.writeParcelable(time, flags);
        dest.writeParcelable(price, flags);
        dest.writeInt(seats);
        dest.writeTypedList(passengers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SharedDriveDAO> CREATOR = new Creator<SharedDriveDAO>() {
        @Override
        public SharedDriveDAO createFromParcel(Parcel in) {
            return new SharedDriveDAO(in);
        }

        @Override
        public SharedDriveDAO[] newArray(int size) {
            return new SharedDriveDAO[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public void addStop(String stop) {
        if (this.stops == null) {
            this.stops = new ArrayList<>();
        }
        this.stops.add(stop);
    }

    public DrivePreferencesDAO getPreferences() {
        return preferences;
    }

    public void setPreferences(DrivePreferencesDAO preferences) {
        this.preferences = preferences;
    }

    public DriveTimeDAO getTime() {
        return time;
    }

    public void setTime(DriveTimeDAO time) {
        this.time = time;
    }

    public DrivePriceDAO getPrice() {
        return price;
    }

    public void setPrice(DrivePriceDAO price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

}
