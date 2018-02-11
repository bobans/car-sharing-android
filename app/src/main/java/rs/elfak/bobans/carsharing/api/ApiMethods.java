package rs.elfak.bobans.carsharing.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
import rs.elfak.bobans.carsharing.models.UploadPhotoResponse;
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
    Single<Token> login();

    @POST(ApiConstants.REGISTER)
    Single<Token> register(@Body Registration registration);

    @GET(ApiConstants.USERS_ME)
    Single<User> getUser();

    @POST(ApiConstants.USERS)
    Single<Response<Void>> createUser(@Body UserDAO user);

    @PUT(ApiConstants.USERS)
    Single<User> updateUser(@Body UserDAO user);

    @DELETE(ApiConstants.USERS)
    Single<Void> deleteUser();

    @Multipart
    @POST(ApiConstants.USERS_UPLOAD_PHOTO)
    Single<UploadPhotoResponse> uploadPhoto(@Part MultipartBody.Part file);

    @GET(ApiConstants.MAKES)
    Single<List<Make>> getMakes();

    @GET(ApiConstants.MODELS)
    Single<List<Model>> getModels(@Path("makeId") long makeId);

    @GET(ApiConstants.SHARED_DRIVES)
    Single<List<SharedDrive>> getSharedDrives(@Query("offset") int offset, @Query("limit") int limit);

    @GET(ApiConstants.SHARED_DRIVES_ID)
    Single<SharedDrive> getSharedDrive(@Path("id") long driveId);

    @GET(ApiConstants.CARS)
    Single<List<Car>> getCars();

    @POST(ApiConstants.SHARED_DRIVES)
    Single<ResponseBody> createSharedDrive(@Body SharedDriveDAO sharedDrive);

    @POST(ApiConstants.SHARED_DRIVES)
    Single<ResponseBody> updateSharedDrive(@Body SharedDrive sharedDrive);

    @DELETE(ApiConstants.SHARED_DRIVES_ID)
    Single<ResponseBody> deleteSharedDrive(@Path("id") long id);

    @POST(ApiConstants.SHARED_DRIVES_REQUEST)
    Single<ResponseBody> requestARide(@Path("driveId") long driveId);

    @DELETE(ApiConstants.SHARED_DRIVES_REQUEST)
    Single<ResponseBody> cancelRideRequest(@Path("driveId") long driveId);

    @PUT(ApiConstants.SHARED_DRIVES_UPDATE_REQUEST)
    Single<ResponseBody> updateRideRequest(@Path("driveId") long driveId, @Path("passengerId") long passengerId, @Path("status") int status);

    @PUT(ApiConstants.FCM_REGISTER)
    Single<ResponseBody> registerFCM(@Body FirebaseToken firebaseToken);

    @DELETE(ApiConstants.FCM_UNREGISTER)
    Single<ResponseBody> unregisterFCM(@Path("device_id") String deviceId);

    @GET(ApiConstants.SHARED_DRIVES_ME)
    Single<List<SharedDrive>> getMyDrives(@Query("offset") int offset, @Query("limit") int limit);

}
