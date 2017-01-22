package rs.elfak.bobans.carsharing.api;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

class ApiConstants {

    static final String BASE_URL_LOCAL = "http://192.168.1.4:8080/";
    static final String BASE_URL_LIVE = "https://car-sharing.herokuapp.com/";

    static final String LOGIN = "login";
    static final String REGISTER = "register";

    static final String USERS_ME = "users/me";
    static final String USERS_CREATE = "users";

    static final String MAKES = "makes";
    static final String MODELS = "makes/{makeId}/models";

    static final String SHARED_DRIVES = "drives";
    static final String SHARED_DRIVES_ID = "drives/{id}";
    static final String SHARED_DRIVES_REQUEST = "drives/{id}/request";
    static final String SHARED_DRIVES_CANCEL_REQUEST = "drives/{id}/request/cancel";

    static final String CARS = "cars";
}
