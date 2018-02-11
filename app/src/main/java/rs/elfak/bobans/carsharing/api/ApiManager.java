package rs.elfak.bobans.carsharing.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.elfak.bobans.carsharing.utils.CarSharingApplication;
import rs.elfak.bobans.carsharing.utils.SessionManager;

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
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        String token = SessionManager.getInstance().getToken();
                        if (token != null) {
                            Request request = chain.request()
                                    .newBuilder()
                                    .header("Authorization", token)
                                    .build();
                            return chain.proceed(request);
                        }
                        return chain.proceed(chain.request());
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(CarSharingApplication.getInstance().getGson()))
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
