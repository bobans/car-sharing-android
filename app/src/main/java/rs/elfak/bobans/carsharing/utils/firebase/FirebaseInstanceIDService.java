package rs.elfak.bobans.carsharing.utils.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.models.FirebaseToken;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {
    private static final String TAG = FirebaseInstanceIdService.class.getSimpleName();

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
            ApiManager.getInstance().getApiMethods().registerFCM(new FirebaseToken(deviceId, refreshedToken))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<ResponseBody>() {
                        @Override
                        public void onSuccess(ResponseBody value) {
                            Log.i(TAG, "Registered");
                        }

                        @Override
                        public void onError(Throwable error) {
                            Log.e(TAG, error.getMessage(), error);
                        }
                    });
        }
    }

}
