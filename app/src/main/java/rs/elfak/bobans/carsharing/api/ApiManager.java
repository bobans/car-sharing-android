package rs.elfak.bobans.carsharing.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.elfak.bobans.carsharing.utils.DateTimeDeserializer;
import rs.elfak.bobans.carsharing.utils.DateTimeSerializer;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ApiManager {

    private static ApiManager instance;

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private Retrofit retrofit;
    private ApiMethods apiMethods;

    private ApiManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiMethods = retrofit.create(ApiMethods.class);
    }

    /**
     * Function for parsing Retrofit HTTP exceptions
     */
    public static ApiError parseError(Response<?> response) {
        try {
            return new ApiError(response.code(), response.message());
        } catch (Exception e) {
            return new ApiError();
        }
    }

    public ApiMethods getApiMethods() {
        return apiMethods;
    }

}
