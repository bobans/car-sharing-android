package rs.elfak.bobans.carsharing.utils;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CarSharingApplication extends Application {

    private static CarSharingApplication instance;

    private Gson gson;

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

        gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
                .create();
    }

    public Gson getGson() {
        return gson;
    }

}
