package rs.elfak.bobans.carsharing.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.elfak.bobans.carsharing.models.Car;
import rs.elfak.bobans.carsharing.models.FirebaseToken;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rs.elfak.bobans.carsharing.models.Registration;
import rs.elfak.bobans.carsharing.models.SharedDrive;
import rs.elfak.bobans.carsharing.models.SharedDriveDAO;
import rs.elfak.bobans.carsharing.models.Token;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rx.Single;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface ApiMethods {

    @POST(ApiConstants.LOGIN)
    Single<Token> login(@Header("Authorization") String token);

    @POST(ApiConstants.REGISTER)
    Single<Token> register(@Body Registration registration);

    @GET(ApiConstants.USERS_ME)
    Single<User> getUser(@Header("Authorization") String token);

    @POST(ApiConstants.USERS)
    Single<Response<Void>> createUser(@Header("Authorization") String token, @Body UserDAO user);

    @PUT(ApiConstants.USERS)
    Single<User> updateUser(@Header("Authorization") String token, @Body UserDAO user);

    @GET(ApiConstants.MAKES)
    Single<List<Make>> getMakes(@Header("Authorization") String token);

    @GET(ApiConstants.MODELS)
    Single<List<Model>> getModels(@Header("Authorization") String token, @Path("makeId") long makeId);

    @GET(ApiConstants.SHARED_DRIVES)
    Single<List<SharedDrive>> getSharedDrives(@Header("Authorization") String token, @Query("offset") int offset, @Query("limit") int limit);

    @GET(ApiConstants.SHARED_DRIVES_ID)
    Single<SharedDrive> getSharedDrive(@Header("Authorization") String token, @Path("id") long driveId);

    @GET(ApiConstants.CARS)
    Single<List<Car>> getCars(@Header("Authorization") String token);

    @POST(ApiConstants.SHARED_DRIVES)
    Single<ResponseBody> createSharedDrive(@Header("Authorization") String token, @Body SharedDriveDAO sharedDrive);

    @POST(ApiConstants.SHARED_DRIVES)
    Single<ResponseBody> updateSharedDrive(@Header("Authorization") String token, @Body SharedDrive sharedDrive);

    @DELETE(ApiConstants.SHARED_DRIVES_ID)
    Single<ResponseBody> deleteSharedDrive(@Header("Authorization") String token, @Path("id") long id);

    @POST(ApiConstants.SHARED_DRIVES_REQUEST)
    Single<ResponseBody> requestARide(@Header("Authorization") String token, @Path("driveId") long driveId);

    @DELETE(ApiConstants.SHARED_DRIVES_REQUEST)
    Single<ResponseBody> cancelRideRequest(@Header("Authorization") String token, @Path("driveId") long driveId);

    @PUT(ApiConstants.SHARED_DRIVES_UPDATE_REQUEST)
    Single<ResponseBody> updateRideRequest(@Header("Authorization") String token, @Path("driveId") long driveId, @Path("passengerId") long passengerId, @Path("status") int status);

    @PUT(ApiConstants.FCM_REGISTER)
    Single<ResponseBody> registerFCM(@Header("Authorization") String token, @Body FirebaseToken firebaseToken);

    @DELETE(ApiConstants.FCM_UNREGISTER)
    Single<ResponseBody> unregisterFCM(@Header("Authorization") String token, @Path("device_id") String deviceId);
}
