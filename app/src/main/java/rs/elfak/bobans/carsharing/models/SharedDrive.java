package rs.elfak.bobans.carsharing.models;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedDrive {

    private transient long id;
    private User user;
    private Car car;
    private String departure;
    private String destination;
    private List<String> stops;
    private DrivePreferences preferences;
    private DriveTime time;
    private DrivePrice price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public DrivePreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(DrivePreferences preferences) {
        this.preferences = preferences;
    }

    public DriveTime getTime() {
        return time;
    }

    public void setTime(DriveTime time) {
        this.time = time;
    }

    public DrivePrice getPrice() {
        return price;
    }

    public void setPrice(DrivePrice price) {
        this.price = price;
    }

}
