package rs.elfak.bobans.carsharing.presenters;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rs.elfak.bobans.carsharing.interactors.ProfileInteractor;
import rs.elfak.bobans.carsharing.models.UploadPhotoResponse;
import rs.elfak.bobans.carsharing.models.User;
import rs.elfak.bobans.carsharing.models.UserDAO;
import rs.elfak.bobans.carsharing.utils.SessionManager;
import rs.elfak.bobans.carsharing.views.IProfileView;
import rx.SingleSubscriber;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */
public class ProfilePresenter extends BasePresenter<IProfileView, ProfileInteractor> {

    @NonNull
    @Override
    protected ProfileInteractor createInteractor() {
        return new ProfileInteractor();
    }

    public void getMyProfile() {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().getMyProfile(new SingleSubscriber<User>() {
            @Override
            public void onSuccess(User value) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().setData(value);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().showError(error, false);
                    getView().showContent();
                }
            }
        });
    }

    public void updateUser(UserDAO user) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        getInteractor().updateUser(user, new SingleSubscriber<User>() {
            @Override
            public void onSuccess(User value) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().setData(value);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().showError(error, false);
                    getView().showContent();
                }
            }
        });
    }

    public void uploadPhoto(File file, MediaType mediaType) {
        if (isViewAttached()) {
            getView().showLoading(false);
        }
        RequestBody requestFile = RequestBody.create(mediaType, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        getInteractor().uploadPhoto(body, new SingleSubscriber<UploadPhotoResponse>() {
            @Override
            public void onSuccess(UploadPhotoResponse value) {
                SessionManager.getInstance().getUser().setPhotoUrl(value.getUrl());
                if (isViewAttached()) {
                    getView().showContent();
                    getView().updatePhoto(value.getUrl());
                }
            }

            @Override
            public void onError(Throwable error) {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().showError(error, false);
                }
            }
        });
    }
}
