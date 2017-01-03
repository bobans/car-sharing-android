package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@SuppressWarnings("WeakerAccess")
public class DrivePreferences extends DrivePreferencesDAO {

    public static final int FLAG_NEGATIVE = -1;
    public static final int FLAG_NEUTRAL = 0;
    public static final int FLAG_POSITIVE = 1;

    private long id;

    public DrivePreferences() {
        this.id = -1;
    }

    protected DrivePreferences(Parcel in) {
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

    public static final Creator<DrivePreferences> CREATOR = new Creator<DrivePreferences>() {
        @Override
        public DrivePreferences createFromParcel(Parcel in) {
            return new DrivePreferences(in);
        }

        @Override
        public DrivePreferences[] newArray(int size) {
            return new DrivePreferences[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
