package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@SuppressWarnings("WeakerAccess")
public class DrivePrice extends DrivePriceDAO {

    public static final int PRICE_TOTAL = 1;
    public static final int PRICE_PER_PASSENGER = 2;

    private long id;

    public DrivePrice() {
        this.id = -1;
    }

    protected DrivePrice(Parcel in) {
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

    public static final Creator<DrivePrice> CREATOR = new Creator<DrivePrice>() {
        @Override
        public DrivePrice createFromParcel(Parcel in) {
            return new DrivePrice(in);
        }

        @Override
        public DrivePrice[] newArray(int size) {
            return new DrivePrice[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
