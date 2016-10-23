package rs.elfak.bobans.carsharing.ui.activities.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;

import rs.elfak.bobans.carsharing.R;
import rs.elfak.bobans.carsharing.interactors.registration.CreateUserInteractor;
import rs.elfak.bobans.carsharing.presenters.registration.CreateUserPresenter;
import rs.elfak.bobans.carsharing.ui.activities.BaseActivity;
import rs.elfak.bobans.carsharing.views.registration.ICreateUserView;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class CreateUserActivity extends BaseActivity<Object, CreateUserInteractor, ICreateUserView, CreateUserPresenter> implements ICreateUserView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
    }

    @NonNull
    @Override
    public CreateUserPresenter createPresenter() {
        return new CreateUserPresenter();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
