package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import rs.elfak.bobans.carsharing.interactors.ResetPasswordInteractor;
import rs.elfak.bobans.carsharing.views.IResetPasswordView;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class ResetPasswordPresenter extends BasePresenter<IResetPasswordView, ResetPasswordInteractor> {

    @NonNull
    @Override
    protected ResetPasswordInteractor createInteractor() {
        return new ResetPasswordInteractor();
    }

    public void resetPassword(final String email) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().resetPassword(email, new SingleSubscriber<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showPasswordReset(email);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                    getView().showContent();
                }
            }
        });
    }

}
