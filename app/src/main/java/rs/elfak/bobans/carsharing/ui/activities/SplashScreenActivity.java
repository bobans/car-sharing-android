package rs.elfak.bobans.carsharing.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.SplashScreenInteractor;
import rs.elfak.bobans.carsharing.presenters.SplashScreenPresenter;
import rs.elfak.bobans.carsharing.ui.activities.registration.CreateUserActivity;
import rs.elfak.bobans.carsharing.ui.dialogs.GenericErrorDialog;
import rs.elfak.bobans.carsharing.ui.dialogs.OneButtonDialog;
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

        loadData(false);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().login();
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginEmailActivity.class));
        finish();
    }

    @Override
    public void showMain() {
        // TODO show main activity
    }

    @Override
    public void showCreateUser() {
        startActivity(new Intent(this, CreateUserActivity.class));
        finish();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        GenericErrorDialog dialog = new GenericErrorDialog(this, R.string.server_error_generic, new OneButtonDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(DialogInterface dialogInterface, View view) {
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
