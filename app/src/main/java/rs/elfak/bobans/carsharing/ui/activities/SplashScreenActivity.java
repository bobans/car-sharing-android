package rs.elfak.bobans.carsharing.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.SplashScreenInteractor;
import rs.elfak.bobans.carsharing.presenters.SplashScreenPresenter;
import rs.elfak.bobans.carsharing.views.ISplashScreenView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SplashScreenActivity extends BaseActivity<Object, SplashScreenInteractor, ISplashScreenView, SplashScreenPresenter> implements ISplashScreenView {

    @NonNull
    @Override
    public SplashScreenPresenter createPresenter() {
        return new SplashScreenPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
