package rs.elfak.bobans.carsharing.utils.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.FirebaseMessageData;
import rs.elfak.bobans.carsharing.models.SharedDriveRequest;
import rs.elfak.bobans.carsharing.ui.activities.HomeActivity;
import rs.elfak.bobans.carsharing.ui.activities.SplashScreenActivity;
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
        createNotification(data.getType(), remoteMessage.getData().get("payload"));
    }

    private void createNotification(FirebaseMessageData.MessageType type, String payload) {
        // TODO
        switch (type) {
            case DRIVE_REQUESTED: {
                SharedDriveRequest request = CarSharingApplication.getInstance().getGson().fromJson(payload, SharedDriveRequest.class);
                createDriveRequestNotification(request);
                break;
            }

            case DRIVE_REQUEST_CANCELED: {
                SharedDriveRequest request = CarSharingApplication.getInstance().getGson().fromJson(payload, SharedDriveRequest.class);
                createDriveRequestCanceledNotification(request);
                break;
            }
        }
    }

    private void createDriveRequestNotification(SharedDriveRequest request) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.putExtra(HomeActivity.EXTRA_SHOW_DRIVE, request.getSharedDrive().getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) request.getSharedDrive().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String driveTitle = getString(R.string.route, request.getSharedDrive().getDeparture(), request.getSharedDrive().getDestination());

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setTicker(getString(R.string.notification_ticker_ride_requested))
                .setSmallIcon(R.drawable.ic_my_drives)
                .setContentTitle(getString(R.string.notification_title_ride_requested))
                .setContentText(getString(R.string.notification_content_ride_requested, request.getPassenger().getUser().getName(), driveTitle))
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) request.getSharedDrive().getId(), notification);
    }

    private void createDriveRequestCanceledNotification(SharedDriveRequest request) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.putExtra(HomeActivity.EXTRA_SHOW_DRIVE, request.getSharedDrive().getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) request.getSharedDrive().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String driveTitle = getString(R.string.route, request.getSharedDrive().getDeparture(), request.getSharedDrive().getDestination());

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setTicker(getString(R.string.notification_ticker_ride_request_canceled))
                .setSmallIcon(R.drawable.ic_my_drives)
                .setContentTitle(getString(R.string.notification_title_ride_request_canceled))
                .setContentText(getString(R.string.notification_content_ride_request_canceled, request.getPassenger().getUser().getName(), driveTitle))
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) request.getSharedDrive().getId(), notification);
    }

}
