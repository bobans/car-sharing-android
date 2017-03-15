package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rs.elfak.bobans.carsharing.interactors.BaseInteractor;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class BasePresenter<V extends IBaseView, I extends BaseInteractor> extends MvpBasePresenter<V> {

    private I interactor;

    public BasePresenter() {
        interactor = createInteractor();
    }

    @NonNull
    protected abstract I createInteractor();

    protected I getInteractor() {
        return interactor;
    }

    public void onDestroy() {
        interactor.onDestroy();
    }

}
