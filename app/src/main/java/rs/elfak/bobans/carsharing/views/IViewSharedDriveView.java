package rs.elfak.bobans.carsharing.views;

import rs.elfak.bobans.carsharing.models.SharedDrive;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public interface IViewSharedDriveView extends IBaseView<SharedDrive> {
    void deleteSuccessful();
    void setIsOwner(boolean isOwner, boolean isFromPast);
    void requestSuccessful();
    void setIsPassenger(boolean isPassenger, boolean isFromPast);
    void requestCanceled();
    void requestUpdated(int adapterPosition, int status);
}
