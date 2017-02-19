package rs.elfak.bobans.carsharing.utils.firebase;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import rs.elfak.bobans.carsharing.models.FirebaseMessageData;
import rs.elfak.bobans.carsharing.utils.CarSharingApplication;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = FirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO remove logs
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());

        FirebaseMessageData data = CarSharingApplication.getInstance().getGson().fromJson(remoteMessage.getData().toString(), FirebaseMessageData.class);
        createNotification(data);
    }

    private void createNotification(FirebaseMessageData data) {
        // TODO
    }

}
