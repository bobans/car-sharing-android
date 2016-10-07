package rs.elfak.bobans.carsharing.utils;

import android.app.Application;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CarSharingApplication extends Application {

    private static CarSharingApplication instance;

    public static CarSharingApplication getInstance() {
        return instance;
    }

    public CarSharingApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

}
