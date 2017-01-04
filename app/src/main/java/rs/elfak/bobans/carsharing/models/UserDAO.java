package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class UserDAO implements Parcelable {

    private String username;
    private String email;
    private String name;
    private String photoUrl;
    private String city;
    private DateTime birthDate;
    private DateTime driverLicenseDate;
    private int userType;
    private List<CarDAO> cars;

    public UserDAO() {
        this.cars = new ArrayList<>();
    }

    protected UserDAO(Parcel in) {
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
        cars = in.createTypedArrayList(CarDAO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public static final Creator<UserDAO> CREATOR = new Creator<UserDAO>() {
        @Override
        public UserDAO createFromParcel(Parcel in) {
            return new UserDAO(in);
        }

        @Override
        public UserDAO[] newArray(int size) {
            return new UserDAO[size];
        }
    };

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

    public List<CarDAO> getCars() {
        return cars;
    }

    public void setCars(List<CarDAO> cars) {
        this.cars = cars;
    }

    public void addCar(CarDAO car) {
        this.cars.add(car);
    }

}
