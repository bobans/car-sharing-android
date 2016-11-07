package rs.elfak.bobans.carsharing.models;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class DrivePrice {

    public static final int PRICE_TOTAL = 1;
    public static final int PRICE_PER_PASSENGER = 2;

    private transient long id;
    private int type;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
