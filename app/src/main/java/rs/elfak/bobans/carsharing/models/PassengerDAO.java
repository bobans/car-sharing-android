package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PassengerDAO implements Parcelable {

    public static final int STATUS_REQUESTED = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_REJECTED = -1;

    private User user;
    private int status;

    public PassengerDAO() {
    }

    protected PassengerDAO(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PassengerDAO> CREATOR = new Creator<PassengerDAO>() {
        @Override
        public PassengerDAO createFromParcel(Parcel in) {
            return new PassengerDAO(in);
        }

        @Override
        public PassengerDAO[] newArray(int size) {
            return new PassengerDAO[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
