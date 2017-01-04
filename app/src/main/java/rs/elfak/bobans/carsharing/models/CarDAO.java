package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CarDAO implements Parcelable {

    private Model model;
    private int year;
    private String registrationPlates;

    public CarDAO() {
    }

    protected CarDAO(Parcel in) {
        model = in.readParcelable(Model.class.getClassLoader());
        year = in.readInt();
        registrationPlates = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(model, flags);
        dest.writeInt(year);
        dest.writeString(registrationPlates);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarDAO> CREATOR = new Creator<CarDAO>() {
        @Override
        public CarDAO createFromParcel(Parcel in) {
            return new CarDAO(in);
        }

        @Override
        public CarDAO[] newArray(int size) {
            return new CarDAO[size];
        }
    };

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegistrationPlates() {
        return registrationPlates;
    }

    public void setRegistrationPlates(String registrationPlates) {
        this.registrationPlates = registrationPlates;
    }

}
