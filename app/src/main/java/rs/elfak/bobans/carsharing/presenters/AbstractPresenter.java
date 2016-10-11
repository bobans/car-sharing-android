package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rs.elfak.bobans.carsharing.interactors.AbstractInteractor;
import rs.elfak.bobans.carsharing.views.IBaseView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public abstract class AbstractPresenter<V extends IBaseView, I extends AbstractInteractor> extends MvpBasePresenter<V> {

    private I interactor;

    public AbstractPresenter() {
        interactor = createInteractor();
    }

    @NonNull
    abstract I createInteractor();

    I getInteractor() {
        return interactor;
    }

}
