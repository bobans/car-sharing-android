package rs.elfak.bobans.carsharing.models;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class DrivePreferences {

    public static final int FLAG_NEGATIVE = -1;
    public static final int FLAG_NEUTRAL = 0;
    public static final int FLAG_POSITIVE = 1;

    private transient long id;
    private int music;
    private int pets;
    private int smoking;
    private int talk;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
