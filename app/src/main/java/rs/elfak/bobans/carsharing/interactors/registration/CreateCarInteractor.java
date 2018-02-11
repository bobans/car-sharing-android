package rs.elfak.bobans.carsharing.interactors.registration;

import java.util.List;

import rs.elfak.bobans.carsharing.api.ApiManager;
import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateCarInteractor extends BaseInteractor {

    public void getMakes(SingleSubscriber<List<Make>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getMakes(), subscriber);
    }

    public void getModels(long makeId, SingleSubscriber<List<Model>> subscriber) {
        subscribe(ApiManager.getInstance().getApiMethods().getModels(makeId), subscriber);
    }

}
