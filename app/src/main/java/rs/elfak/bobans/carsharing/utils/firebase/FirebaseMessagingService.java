package rs.elfak.bobans.carsharing.utils.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.models.FirebaseMessageData;
import rs.elfak.bobans.carsharing.models.SharedDrive;
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

    private static final int NOTIFICATION_DRIVE_REQUEST = 6001;
    private static final int NOTIFICATION_NEW_DRIVE = 6002;

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

            case NEW_DRIVE: {
                SharedDrive drive = CarSharingApplication.getInstance().getGson().fromJson(payload, SharedDrive.class);
                createNewSharedDriveNotification(drive);
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
        String contentText = getString(R.string.notification_content_ride_requested, request.getPassenger().getUser().getName(), driveTitle);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setTicker(getString(R.string.notification_ticker_ride_requested))
                .setSmallIcon(R.drawable.ic_my_drives)
                .setContentTitle(getString(R.string.notification_title_ride_requested))
                .setContentText(contentText)
                .setStyle(new Notification.BigTextStyle().bigText(contentText))
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(String.valueOf(request.getSharedDrive().getId()), NOTIFICATION_DRIVE_REQUEST, notification);
    }

    private void createDriveRequestCanceledNotification(SharedDriveRequest request) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.putExtra(HomeActivity.EXTRA_SHOW_DRIVE, request.getSharedDrive().getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) request.getSharedDrive().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String driveTitle = getString(R.string.route, request.getSharedDrive().getDeparture(), request.getSharedDrive().getDestination());
        String contentText = getString(R.string.notification_content_ride_request_canceled, request.getPassenger().getUser().getName(), driveTitle);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setTicker(getString(R.string.notification_ticker_ride_request_canceled))
                .setSmallIcon(R.drawable.ic_my_drives)
                .setContentTitle(getString(R.string.notification_title_ride_request_canceled))
                .setContentText(contentText)
                .setStyle(new Notification.BigTextStyle().bigText(contentText))
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(String.valueOf(request.getSharedDrive().getId()), NOTIFICATION_DRIVE_REQUEST, notification);
    }

    private void createNewSharedDriveNotification(SharedDrive drive) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.putExtra(HomeActivity.EXTRA_SHOW_DRIVE, drive.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) drive.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String contentText = getString(R.string.notification_content_new_ride, drive.getUser().getName(), drive.getDeparture(), drive.getDestination());

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setTicker(getString(R.string.notification_ticker_new_ride))
                .setSmallIcon(R.drawable.ic_my_drives)
                .setContentTitle(getString(R.string.notification_title_new_ride))
                .setContentText(contentText)
                .setStyle(new Notification.BigTextStyle().bigText(contentText))
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(String.valueOf(drive.getId()), NOTIFICATION_NEW_DRIVE, notification);
    }

}
