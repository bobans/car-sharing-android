package rs.elfak.bobans.carsharing.api;

import rs.elfak.bobans.carsharing.BuildConfig;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
class ApiConstants {

    static final String BASE_URL = BuildConfig.BASE_URL;

    static final String LOGIN = "login";
    static final String REGISTER = "register";
    static final String RESET_PASSWORD = "reset-password";

    static final String USERS_ME = "users/me";
    static final String USERS = "users";
    static final String USERS_UPLOAD_PHOTO = "users/upload-photo";
    static final String USERS_FILTER = "users/filter";

    static final String MAKES = "makes";
    static final String MODELS = "makes/{makeId}/models";

    static final String SHARED_DRIVES = "drives";
    static final String SHARED_DRIVES_FILTERED = "drives/filter";
    static final String SHARED_DRIVES_ME = "drives/user/me";
    static final String SHARED_DRIVES_ID = "drives/{id}";
    static final String SHARED_DRIVES_REQUEST = "drives/{driveId}/request";
    static final String SHARED_DRIVES_UPDATE_REQUEST = "drives/{driveId}/request/{passengerId}/{status}";

    static final String CARS = "cars";

    static final String FCM_REGISTER = "fcm/register";
    static final String FCM_UNREGISTER = "fcm/unregister/{device_id}";
}
