package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class DriveTimeDAO implements Parcelable {

    protected boolean repeat;
    protected String repeatDays;
    protected DateTime date;
    protected DateTime departureTime;
    protected DateTime arrivalTime;

    public DriveTimeDAO() {
    }

    protected DriveTimeDAO(Parcel in) {
        repeat = in.readByte() != 0;
        repeatDays = in.readString();
        long dateMillis = in.readLong();
        if (dateMillis > 0) {
            date = new DateTime(dateMillis);
        }
        long departureMillis = in.readLong();
        if (departureMillis > 0) {
            departureTime = new DateTime(departureMillis);
        }
        long arrivalMillis = in.readLong();
        if (arrivalMillis > 0) {
            arrivalTime = new DateTime(arrivalMillis);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (repeat ? 1 : 0));
        dest.writeString(repeatDays);
        dest.writeLong(date != null ? date.getMillis() : 0);
        dest.writeLong(departureTime != null ? departureTime.getMillis() : 0);
        dest.writeLong(arrivalTime != null ? arrivalTime.getMillis() : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DriveTimeDAO> CREATOR = new Creator<DriveTimeDAO>() {
        @Override
        public DriveTimeDAO createFromParcel(Parcel in) {
            return new DriveTimeDAO(in);
        }

        @Override
        public DriveTimeDAO[] newArray(int size) {
            return new DriveTimeDAO[size];
        }
    };

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
        this.departureTime = departureTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}
