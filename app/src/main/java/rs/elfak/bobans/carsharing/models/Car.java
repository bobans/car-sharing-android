package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class Car extends CarDAO {

    private long id;

    public Car() {
        this.id = -1;
    }

    protected Car(Parcel in) {
        super(in);
        id = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
