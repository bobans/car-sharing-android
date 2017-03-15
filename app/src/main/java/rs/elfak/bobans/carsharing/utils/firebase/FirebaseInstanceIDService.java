package rs.elfak.bobans.carsharing.utils.firebase;

import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.FirebaseToken;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        sendTokenUpdate();
    }

    public static void sendTokenUpdate() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Get device id
        String deviceId = FirebaseInstanceId.getInstance().getId();
        if (refreshedToken != null && !refreshedToken.isEmpty()) {
            Observable<ResponseBody> register = ApiManager.getInstance().getApiMethods().registerFCM(SessionManager.getInstance().getToken(), new FirebaseToken(deviceId, refreshedToken));
            register.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

}
