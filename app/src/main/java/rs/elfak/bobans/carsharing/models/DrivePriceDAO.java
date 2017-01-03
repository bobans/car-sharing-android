package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class DrivePriceDAO implements Parcelable {

    protected int type;
    protected double price;

    public DrivePriceDAO() {
    }

    protected DrivePriceDAO(Parcel in) {
        type = in.readInt();
        price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DrivePriceDAO> CREATOR = new Creator<DrivePriceDAO>() {
        @Override
        public DrivePriceDAO createFromParcel(Parcel in) {
            return new DrivePriceDAO(in);
        }

        @Override
        public DrivePriceDAO[] newArray(int size) {
            return new DrivePriceDAO[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
