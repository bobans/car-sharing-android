package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@SuppressWarnings("unused")
public class User extends UserDAO {

    public static final int TYPE_PASSENGER = 1;
    public static final int TYPE_DRIVER = 2;

    private long id;

    public User() {
        this.id = -1;
    }

    protected User(Parcel in) {
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

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
