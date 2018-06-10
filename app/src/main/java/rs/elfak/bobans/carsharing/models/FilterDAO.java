package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FilterDAO implements Parcelable {

    private String start;
    private String stop;

    public FilterDAO(String start, String stop) {
        this.start = start;
        this.stop = stop;
    }

    protected FilterDAO(Parcel in) {
        start = in.readString();
        stop = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(start);
        dest.writeString(stop);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterDAO> CREATOR = new Creator<FilterDAO>() {
        @Override
        public FilterDAO createFromParcel(Parcel in) {
            return new FilterDAO(in);
        }

        @Override
        public FilterDAO[] newArray(int size) {
            return new FilterDAO[size];
        }
    };

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

}
