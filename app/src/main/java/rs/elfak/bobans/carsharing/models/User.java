package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class User implements Parcelable {

    public static final int TYPE_PASSENGER = 1;
    public static final int TYPE_DRIVER = 2;

    private long id;
    private String username;
    private String email;
    private String name;
    private String photoUrl;
    private String city;
    private DateTime birthDate;
    private DateTime driverLicenseDate;
    private int userType;
    private List<Car> cars;

    public User() {
        this.id = -1;
        this.cars = new ArrayList<>();
    }

    protected User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        email = in.readString();
        name = in.readString();
        photoUrl = in.readString();
        city = in.readString();
        long birthMillis = in.readLong();
        if (birthMillis > 0) {
            birthDate = new DateTime(birthMillis);
        }
        long driverLicenseMillis = in.readLong();
        if (driverLicenseMillis > 0) {
            driverLicenseDate = new DateTime(driverLicenseMillis);
        }
        userType = in.readInt();
        cars = in.createTypedArrayList(Car.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(photoUrl);
        dest.writeString(city);
        dest.writeLong(birthDate != null ? birthDate.getMillis() : 0);
        dest.writeLong(driverLicenseDate != null ? driverLicenseDate.getMillis() : 0);
        dest.writeInt(userType);
        dest.writeTypedList(cars);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public DateTime getDriverLicenseDate() {
        return driverLicenseDate;
    }

    public void setDriverLicenseDate(DateTime driverLicenseDate) {
        this.driverLicenseDate = driverLicenseDate;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

}
