package rs.elfak.bobans.carsharing.views;

import rs.elfak.bobans.carsharing.models.SharedDrive;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public interface IViewSharedDriveView extends IBaseView<SharedDrive> {
    void deleteSuccessful();
    void setIsOwner(boolean isOwner);
    void requestSuccessful();
    void setIsPassenger(boolean isPassenger);
    void requestCanceled();
}
