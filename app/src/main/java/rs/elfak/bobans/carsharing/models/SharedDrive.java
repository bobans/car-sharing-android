package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class SharedDrive extends SharedDriveDAO {

    private long id;

    public SharedDrive() {
        this.id = -1;
    }

    protected SharedDrive(Parcel in) {
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

    public static final Creator<SharedDrive> CREATOR = new Creator<SharedDrive>() {
        @Override
        public SharedDrive createFromParcel(Parcel in) {
            return new SharedDrive(in);
        }

        @Override
        public SharedDrive[] newArray(int size) {
            return new SharedDrive[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
