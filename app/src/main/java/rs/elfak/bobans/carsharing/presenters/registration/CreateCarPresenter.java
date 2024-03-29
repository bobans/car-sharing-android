package rs.elfak.bobans.carsharing.presenters.registration;

import android.support.annotation.NonNull;

import java.util.List;

import rs.elfak.bobans.carsharing.interactors.registration.CreateCarInteractor;
import rs.elfak.bobans.carsharing.models.Make;
import rs.elfak.bobans.carsharing.models.Model;
import rs.elfak.bobans.carsharing.presenters.BasePresenter;
import rs.elfak.bobans.carsharing.views.registration.ICreateCarView;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateCarPresenter extends BasePresenter<ICreateCarView, CreateCarInteractor> {

    @NonNull
    @Override
    protected CreateCarInteractor createInteractor() {
        return new CreateCarInteractor();
    }

    public void loadMakes() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().getMakes(new SingleSubscriber<List<Make>>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onSuccess(List<Make> makes) {
                if (isViewAttached()) {
                    getView().setMakes(makes);
                    getView().showContent();
                }
            }
        });
    }

    public void loadModels(long makeId) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().getModels(makeId, new SingleSubscriber<List<Model>>() {
            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }

            @Override
            public void onSuccess(List<Model> models) {
                if (isViewAttached()) {
                    getView().setModels(models);
                    getView().showContent();
                }
            }
        });
    }

}
