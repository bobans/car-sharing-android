package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@SuppressWarnings("WeakerAccess")
public class DriveTime extends DriveTimeDAO {

    private long id;

    public DriveTime() {
        this.id = -1;
    }

    protected DriveTime(Parcel in) {
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

    public static final Creator<DriveTime> CREATOR = new Creator<DriveTime>() {
        @Override
        public DriveTime createFromParcel(Parcel in) {
            return new DriveTime(in);
        }

        @Override
        public DriveTime[] newArray(int size) {
            return new DriveTime[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
