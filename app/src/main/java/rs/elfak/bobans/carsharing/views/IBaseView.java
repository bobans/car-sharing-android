package rs.elfak.bobans.carsharing.views;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public interface IBaseView<M> extends MvpLceView<M> {
    void showNoInternetConnection();
    void navigateToActivity(Class activityClass, Bundle extras);
    void navigateToActivityClearStack(Class activityClass, Bundle extras);
    void navigateToActivityForResult(int requestCode, Class activityClass, Bundle extras);
    void finishActivity();
}
