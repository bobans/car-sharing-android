package rs.elfak.bobans.carsharing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class DrivePreferencesDAO implements Parcelable {

    protected int music;
    protected int pets;
    protected int smoking;
    protected int talk;

    public DrivePreferencesDAO() {
    }

    protected DrivePreferencesDAO(Parcel in) {
        music = in.readInt();
        pets = in.readInt();
        smoking = in.readInt();
        talk = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(music);
        dest.writeInt(pets);
        dest.writeInt(smoking);
        dest.writeInt(talk);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DrivePreferencesDAO> CREATOR = new Creator<DrivePreferencesDAO>() {
        @Override
        public DrivePreferencesDAO createFromParcel(Parcel in) {
            return new DrivePreferencesDAO(in);
        }

        @Override
        public DrivePreferencesDAO[] newArray(int size) {
            return new DrivePreferencesDAO[size];
        }
    };

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getPets() {
        return pets;
    }

    public void setPets(int pets) {
        this.pets = pets;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public int getTalk() {
        return talk;
    }

    public void setTalk(int talk) {
        this.talk = talk;
    }

}
