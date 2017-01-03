package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class Make implements Parcelable {

    private long id;
    private String title;

    public Make() {
        this.id = -1;
    }

    protected Make(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Make> CREATOR = new Creator<Make>() {
        @Override
        public Make createFromParcel(Parcel in) {
            return new Make(in);
        }

        @Override
        public Make[] newArray(int size) {
            return new Make[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
