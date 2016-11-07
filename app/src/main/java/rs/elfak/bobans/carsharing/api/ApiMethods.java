package rs.elfak.bobans.carsharing.api;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rs.elfak.bobans.carsharing.models.Registration;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.models.User;
import rx.Observable;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ApiMethods {

    @POST(ApiConstants.LOGIN)
    Observable<Token> login(@Header("Authorization") String token);

    @POST(ApiConstants.REGISTER)
    Observable<Token> register(@Body Registration registration);

    @GET(ApiConstants.USERS_ME)
    Observable<User> getUser(@Header("Authorization") String token);

    @POST(ApiConstants.USERS_CREATE)
    Observable<Response<Void>> createUser(@Header("Authorization") String token, @Body User user);

    @GET(ApiConstants.MAKES)
    Observable<List<Make>> getMakes(@Header("Authorization") String token);

    @GET(ApiConstants.MODELS)
    Observable<List<Model>> getModels(@Header("Authorization") String token, @Path("makeId") long makeId);

    @GET(ApiConstants.SHARED_DRIVES)
    Observable<List<SharedDrive>> getSharedDrives(@Header("Authorization") String token, @Query("offset") int offset, @Query("limit") int limit);

}
